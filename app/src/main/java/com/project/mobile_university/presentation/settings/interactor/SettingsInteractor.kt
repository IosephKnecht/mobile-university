package com.project.mobile_university.presentation.settings.interactor

import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.UserMapper
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class SettingsInteractor(
    private val loginRepository: LoginRepository,
    exceptionConverter: ExceptionConverter
) : InteractorWithErrorHandler<SettingsContract.Listener>(exceptionConverter), SettingsContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getUserInfo() {
        compositeDisposable.add(simpleDiscardResult(loginRepository.getUserInfo()) { listener, result ->
            listener?.onObtainUserInfo(result.data, result.throwable)
        })
    }

    override fun logout() {
        compositeDisposable.add(simpleDiscardResult(loginRepository.logout()) { listener, throwable ->
            listener?.onExit(throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}