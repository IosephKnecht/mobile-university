package com.project.mobile_university.domain.adapters.retrofit

import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxErrorCallFactory private constructor(private val exceptionAdapter: ExceptionAdapter) : CallAdapter.Factory() {
    companion object {
        private lateinit var original: RxJava2CallAdapterFactory

        fun create(exceptionAdapter: ExceptionAdapter): CallAdapter.Factory {
            return RxErrorCallFactory(exceptionAdapter)
        }
    }

    init {
        original = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        return RxErrorCallAdapter(
            (original
                .get(returnType, annotations, retrofit) as CallAdapter<out Any, Any>?)!!,
            exceptionAdapter
        )
    }

    private class RxErrorCallAdapter<R>(
        private val wrapped: CallAdapter<R, Any>,
        private val exceptionAdapter: ExceptionAdapter
    ) : CallAdapter<R, Any> {
        override fun adapt(call: Call<R>): Any {
            val parsed = wrapped.adapt(call)

            return when (parsed) {
                is Observable<*> -> {
                    parsed.onErrorResumeNext(Function { throwable ->
                        Observable.error(exceptionAdapter.mathToAppException(throwable))
                    })
                }
                is Single<*> -> {
                    parsed.onErrorResumeNext(Function { throwable ->
                        Single.error(exceptionAdapter.mathToAppException(throwable))
                    })
                }
                is Completable -> {
                    parsed.onErrorResumeNext { throwable ->
                        Completable.error(
                            exceptionAdapter.mathToAppException(
                                throwable
                            )
                        )
                    }
                }
                else -> {
                    throw RuntimeException("wrapped adapt return not observable")
                }
            }
        }

        override fun responseType(): Type = wrapped.responseType()
    }
}