package com.project.mobile_university.presentation.common

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class InteractorWithErrorHandler<L : MvpInteractor.Listener>(private val exceptionConverter: ExceptionConverter) :
    AbstractInteractor<L>() {

    protected fun <R, O : Observable<R>> simpleDiscardResult(
        observable: O,
        throwableConverter: (listener: L?, result: PendingResult<R>) -> Unit
    ): Disposable {

        return discardResult(observable) { listener, result ->
            val data = result.data
            val convertedThrowable = result.throwable?.run { Throwable(exceptionConverter.convert(this)) }

            throwableConverter.invoke(
                listener, PendingResult(data, convertedThrowable)
            )
        }
    }

    protected fun <R, O : Single<R>> simpleDiscardResult(
        observable: O,
        throwableConverter: (listener: L?, result: PendingResult<R>) -> Unit
    ): Disposable {

        return discardResult(observable) { listener, result ->
            val data = result.data
            val convertedThrowable = result.throwable?.run { Throwable(exceptionConverter.convert(this)) }

            throwableConverter.invoke(
                listener, PendingResult(data, convertedThrowable)
            )
        }
    }

    protected fun simpleDiscardResult(
        observable: Completable,
        throwableConverter: (listener: L?, throwable: Throwable?) -> Unit
    ): Disposable {

        return discardResult(observable) { listener, throwable ->
            val convertedThrowable = throwable?.run {
                Throwable(exceptionConverter.convert(this))
            }

            throwableConverter.invoke(listener, convertedThrowable)
        }
    }
}