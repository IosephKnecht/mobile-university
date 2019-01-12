package com.project.mobile_university.presentation.login.presenter

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginPresenter(private val interactor: LoginContract.Interactor) : AbstractPresenter(), LoginContract.Presenter,
    LoginContract.Listener {

    override val enterEnabled = MutableLiveData<Boolean>()
    override val state = MutableLiveData<LoginContract.State>()

    override val serviceUrl = MutableLiveData<String>()
    override val login = MutableLiveData<String>()
    override val password = MutableLiveData<String>()

    private val params = MediatorLiveData<String>()


    init {
        // FIXME: default value init exist in androidx.extensions
        state.value = LoginContract.State.NOT_AUTHORIZE

        params.addSource(serviceUrl) {
            params.postValue(it)
        }

        params.addSource(login) {
            params.postValue(it)
        }

        params.addSource(password) {
            params.postValue(it)
        }
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        registerObservers(enterEnabled, state)
        interactor.setListener(this)

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

            enterEnabled.postValue(serviceCondition &&
                    loginCondition &&
                    passwordCondition)
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

    override fun onSaveServerConfig(serviceUrl: String, throwable: Throwable?) {
        if (throwable == null && serviceUrl.isNotEmpty()) {
            this.serviceUrl.postValue(serviceUrl)
            interactor.setServiceUrl(serviceUrl)
        }
    }
}