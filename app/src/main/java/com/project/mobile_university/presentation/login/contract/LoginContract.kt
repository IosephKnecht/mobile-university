package com.project.mobile_university.presentation.login.contract

import android.arch.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter

interface LoginContract {
    interface ObservableStorage {
        val enterEnabled: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter {
        fun tryLogin(login: String, password: String)
        fun saveServiceUrl(serviceUrl: String)
        fun saveLogin(login: String)
        fun savePassword(password: String)
    }

    interface Listener : MvpInteractor.Listener {
        fun onLogin(isAuth: Boolean?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun login(login: String, password: String)
        fun setServiceUrl(serviceUrl: String)
    }
}