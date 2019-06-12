package com.project.mobile_university.presentation.settings.interactor

import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.UserMapper
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class SettingsInteractor(private val sharedPreferenceService: SharedPreferenceService,
                         exceptionConverter: ExceptionConverter)
    : InteractorWithErrorHandler<SettingsContract.Listener>(exceptionConverter), SettingsContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getUserInfo() {
        val observable = Observable.fromCallable { sharedPreferenceService.getUserInfo() }
            .map { UserMapper.toPresentation(it) }

        compositeDisposable.add(simpleDiscardResult(observable) { listener, result ->
            listener?.onObtainUserInfo(result.data, result.throwable)
        })
    }

    override fun logout() {
        val observable = Observable.fromCallable { sharedPreferenceService.removeLoginPassString() }
            .map { sharedPreferenceService.removeUserInfo() }

        compositeDisposable.add(simpleDiscardResult(observable) { listener, result ->
            listener?.onExit(result.throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}