package com.project.mobile_university.presentation.login.contract

import android.arch.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent

interface LoginContract {
    enum class State {
        NOT_AUTHORIZE,
        PROCESSING_AUTHORIZE,
        SUCCESS_AUTHORIZE,
        ERROR_AUTHORIZE,
        FAILED_AUTHORIZE
    }

    interface ObservableStorage {
        val enterEnabled: LiveData<Boolean>
        val state: LiveData<State>
    }

    interface Presenter : MvpPresenter {
        fun tryLogin()
        fun setParam(key: String, value: String)
    }

    interface Listener : MvpInteractor.Listener {
        fun onLogin(isAuth: Boolean?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun login(login: String, password: String)
        fun setServiceUrl(serviceUrl: String)
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener> {
        fun showNextScreen(androidComponent: AndroidComponent)
    }
}