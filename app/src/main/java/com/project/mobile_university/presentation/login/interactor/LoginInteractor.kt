package com.project.mobile_university.presentation.login.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginInteractor(private val apiService: ApiService) : AbstractInteractor<LoginContract.Listener>(), LoginContract.Interactor {

    override fun login(login: String, password: String) {
        val observable = apiService.login(login, password)

        discardResult(observable) { listener, result ->
            result.apply {
                when {
                    data != null && data!!.objectList!!.isNotEmpty() -> listener!!.onLogin(true, null)
                    throwable != null -> listener!!.onLogin(null, throwable)
                    else -> listener!!.onLogin(false, null)
                }
            }
        }
    }

    override fun setServiceUrl(serviceUrl: String) {
        apiService.serviceUrl = serviceUrl
    }
}