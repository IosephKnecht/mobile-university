package com.project.mobile_university.presentation.login.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ServerConfig

interface LoginContract {
    enum class State {
        IDLE,
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
        fun obtainServerConfig()
    }

    interface Listener : MvpInteractor.Listener {
        fun onLogin(isAuth: Boolean?, throwable: Throwable?)
        fun onSaveServerConfig(serviceUrl: String, throwable: Throwable?)
        fun onObtainServerConfig(serverConfig: ServerConfig?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun login(login: String, password: String)
        fun setServiceUrl(serviceUrl: String)
        fun saveServerConfig(serverConfig: ServerConfig)
        fun getServerConfig()
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener> {
        fun showNextScreen(androidComponent: AndroidComponent)
    }
}