package com.project.mobile_university.presentation.login.presenter

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.presentation.addSource
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginPresenter(private val interactor: LoginContract.Interactor) : AbstractPresenter(), LoginContract.Presenter,
    LoginContract.Listener {

    override val enterEnabled = MutableLiveData<Boolean>()
    override val state = MutableLiveData<LoginContract.State>(LoginContract.State.IDLE)

    override val serviceUrl = MutableLiveData<String>()
    override val login = MutableLiveData<String>()
    override val password = MutableLiveData<String>()

    private val params = MediatorLiveData<String>()


    init {
        params.apply {
            addSource(serviceUrl)
            addSource(login)
            addSource(password)
        }
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        registerObservers(enterEnabled, state, serviceUrl, login, password, params)
        interactor.setListener(this)

        if (state.value == LoginContract.State.IDLE) {
            obtainServerConfig()
        }

        params.observe(androidComponent) {
            val serviceCondition = serviceUrl.value?.run {
                isNotEmpty()
            } ?: false

            val loginCondition = login.value?.run {
                length > 3
            } ?: false

            val passwordCondition = password.value?.run {
                length > 3
            } ?: false

            enterEnabled.postValue(
                serviceCondition &&
                        loginCondition &&
                        passwordCondition
            )
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        unregisterObservers()
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun tryLogin() {
        state.postValue(LoginContract.State.PROCESSING_AUTHORIZE)
        interactor.login(login.value!!, password.value!!)
    }

    override fun onChangeServerConfig(serverConfig: ServerConfig) {
        interactor.saveServerConfig(serverConfig)
    }

    override fun obtainServerConfig() {
        interactor.getServerConfig()
    }

    override fun onLogin(isAuth: Boolean?, throwable: Throwable?) {
        if (throwable != null) {
            state.postValue(LoginContract.State.ERROR_AUTHORIZE)
        } else if (isAuth != null) {
            if (isAuth) {
                state.postValue(LoginContract.State.SUCCESS_AUTHORIZE)
            } else {
                state.postValue(LoginContract.State.FAILED_AUTHORIZE)
            }
        } else {
            state.postValue(LoginContract.State.NOT_AUTHORIZE)
        }
    }

    override fun onObtainServerConfig(serverConfig: ServerConfig?, throwable: Throwable?) {
        if (throwable == null && serverConfig != null) {
            val serverConfigString = serverConfig.toString()
            this.serviceUrl.postValue(serverConfigString)
            interactor.setServiceUrl(serverConfigString)
        }
        state.postValue(LoginContract.State.NOT_AUTHORIZE)
    }
}