package com.project.mobile_university.presentation.login.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.presentation.login.contract.LoginContract
import io.reactivex.Observable

class LoginInteractor(private val apiService: ApiService,
                      private val sharedPreferenceService: SharedPreferenceService) : AbstractInteractor<LoginContract.Listener>(), LoginContract.Interactor {

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

    override fun saveServerConfig(serverConfig: ServerConfig) {
        val observable = Observable.fromCallable {
            sharedPreferenceService.saveServerConfig(serverConfig)
        }.map {
            sharedPreferenceService.getServerConfig()
        }

        discardResult(observable) { listener, result ->
            result.apply {
                when {
                    data != null -> {
                        listener!!.onSaveServerConfig(result.data.toString(), throwable)
                    }
                    else -> {
                        listener!!.onSaveServerConfig("", throwable)
                    }
                }
            }
        }
    }
}