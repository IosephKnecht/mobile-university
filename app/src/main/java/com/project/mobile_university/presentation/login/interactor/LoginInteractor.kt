package com.project.mobile_university.presentation.login.interactor

import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginInteractor(
    private val loginRepository: LoginRepository,
    errorHandler: ExceptionConverter
) : InteractorWithErrorHandler<LoginContract.Listener>(errorHandler), LoginContract.Interactor {

    override fun login(login: String, password: String) {
        simpleDiscardResult(loginRepository.login(login, password)) { listener, result ->
            listener?.onLogin(result.data, result.throwable)
        }
    }

    override fun setServiceUrl(serviceUrl: String) {
        discardResult(loginRepository.setServiceUrl(serviceUrl)) { _, throwable ->
            throwable?.printStackTrace()
        }
    }

    override fun saveServerConfig(serverConfig: ServerConfig) {
        discardResult(loginRepository.saveServerConfig(serverConfig)) { listener, result ->
            result.apply {
                listener?.onObtainServerConfig(data, throwable)
            }
        }
    }

    override fun getServerConfig() {
        simpleDiscardResult(loginRepository.getServerConfig()) { listener, result ->
            listener?.onObtainServerConfig(result.data, result.throwable)
        }
    }

    override fun saveLoginPassString(login: String, pass: String) {
        discardResult(loginRepository.saveLoginPass(login, pass)) { _, throwable ->
            throwable?.printStackTrace()
        }
    }
}