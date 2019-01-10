package com.project.mobile_university.presentation.login.presenter

import android.arch.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginPresenter(private val interactor: LoginContract.Interactor) : AbstractPresenter(), LoginContract.Presenter,
    LoginContract.Listener, LoginContract.ObservableStorage {

    private val paramsMap = mutableMapOf<String, String>()

    companion object {
        const val URL_PARAM = "service url"
        const val LOGIN_PARAM = "login"
        const val PASS_PARAM = "password"
    }

    override val enterEnabled = MutableLiveData<Boolean>()
    override val state = MutableLiveData<LoginContract.State>()

    init {
        // FIXME: default value init exist in androidx.extensions
        state.value = LoginContract.State.NOT_AUTHORIZE
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        registerObservers(enterEnabled, state)
        interactor.setListener(this)
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
        interactor.login(paramsMap[LOGIN_PARAM]!!, paramsMap[PASS_PARAM]!!)
    }

    override fun setParam(key: String, value: String) {
        if (key == URL_PARAM) {
            if (value.isNotEmpty())
                interactor.setServiceUrl("http://$value")
        }
        paramsMap[key] = value
        checkEnabled()
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

    private fun checkEnabled() {
        val serviceUrl = paramsMap[URL_PARAM] ?: ""
        val login = paramsMap[LOGIN_PARAM] ?: ""
        val password = paramsMap[PASS_PARAM] ?: ""

        enterEnabled.postValue(serviceUrl.isNotEmpty() &&
            login.isNotEmpty() && login.length >= 4 &&
            password.isNotEmpty() && password.length >= 4)
    }
}