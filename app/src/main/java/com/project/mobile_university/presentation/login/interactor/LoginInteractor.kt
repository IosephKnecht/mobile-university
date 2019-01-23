package com.project.mobile_university.presentation.login.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.domain.utils.AuthUtil
import com.project.mobile_university.presentation.login.contract.LoginContract
import io.reactivex.Observable

class LoginInteractor(
    private val apiService: ApiService,
    private val sharedPreferenceService: SharedPreferenceService
) : AbstractInteractor<LoginContract.Listener>(), LoginContract.Interactor {

    override fun login(login: String, password: String) {
        val observable = apiService.login(login, password)
            .map { serverResponse ->
                serverResponse.objectList?.takeIf { it.isNotEmpty() }
                    ?.let { sharedPreferenceService.saveUserInfo(it[0]) }
                serverResponse
            }

        discardResult(observable) { listener, result ->
            result.apply {
                when {
                    data != null -> {
                        if (data!!.objectList != null && data!!.objectList!!.isNotEmpty()) {
                            val user = data!!.objectList!![0]
                            listener!!.onLogin(user, null)
                        }
                    }
                    throwable != null -> listener!!.onLogin(null, throwable)
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
                listener!!.onObtainServerConfig(data, throwable)
            }
        }
    }

    override fun getServerConfig() {
        val observable = Observable.fromCallable {
            sharedPreferenceService.getServerConfig()
        }

        discardResult(observable) { listener, result ->
            listener!!.onObtainServerConfig(result.data, result.throwable)
        }
    }

    override fun saveLoginPassString(login: String, pass: String) {
        AuthUtil.convertToBase64(login, pass).let {
            sharedPreferenceService.saveLoginPassString(it)
        }
    }
}