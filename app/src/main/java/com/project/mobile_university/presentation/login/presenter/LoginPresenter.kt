package com.project.mobile_university.presentation.login.presenter

import android.arch.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginPresenter(private val interactor: LoginContract.Interactor) : AbstractPresenter(), LoginContract.Presenter,
    LoginContract.Listener, LoginContract.ObservableStorage {

    private var serviceUrl = ""
    private var login: String = ""
    private var password: String = ""

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

    override fun saveLogin(login: String) {
        this.login = login
        checkEnabled()
    }

    override fun savePassword(password: String) {
        this.password = password
        checkEnabled()
    }

    override fun saveServiceUrl(serviceUrl: String) {
        this.serviceUrl = serviceUrl
        interactor.setServiceUrl(serviceUrl)
        checkEnabled()
    }

    override fun onLogin(isAuth: Boolean?, throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun checkEnabled() {
        enterEnabled.postValue(serviceUrl.isNotEmpty() &&
            login.isNotEmpty() && login.length >= 4 &&
            password.isNotEmpty() && password.length >= 4)
    }
}