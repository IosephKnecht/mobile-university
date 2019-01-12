package com.project.mobile_university.presentation.login.contract

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ServerConfig

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
        val login: MutableLiveData<String>
        val password: MutableLiveData<String>
        val serviceUrl: LiveData<String>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun tryLogin()
        fun onChangeServerConfig(serverConfig: ServerConfig)
    }

    interface Listener : MvpInteractor.Listener {
        fun onLogin(isAuth: Boolean?, throwable: Throwable?)
        fun onSaveServerConfig(serviceUrl: String, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun login(login: String, password: String)
        fun setServiceUrl(serviceUrl: String)
        fun saveServerConfig(serverConfig: ServerConfig)
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener> {
        fun showNextScreen(androidComponent: AndroidComponent)
    }
}