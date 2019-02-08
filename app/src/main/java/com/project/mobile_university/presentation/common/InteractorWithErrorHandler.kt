package com.project.mobile_university.presentation.common

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

abstract class InteractorWithErrorHandler<L : MvpInteractor.Listener>(private val exceptionConverter: ExceptionConverter)
    : AbstractInteractor<L>() {

    protected fun <R, O : Observable<R>> simpleDiscardResult(observable: O,
                                                             block: (listener: L?, result: PendingResult<R>) -> Unit): Disposable {

        return discardResult(observable) { listener, result ->
            val throwable = result.throwable

            block(listener, if (throwable != null) PendingResult(null,
                Throwable(exceptionConverter.convert(throwable))) as @ParameterName(name = "result") PendingResult<R>
            else result)
        }
    }
}