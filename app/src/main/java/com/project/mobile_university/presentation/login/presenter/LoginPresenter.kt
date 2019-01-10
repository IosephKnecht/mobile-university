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

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun tryLogin(login: String, password: String) {
        interactor.login(login, password)
    }

    override fun setParam(key: String, value: String) {
        paramsMap[key] = value
        checkEnabled()
    }

    override fun onLogin(isAuth: Boolean?, throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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