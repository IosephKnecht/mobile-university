package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.AuthUtil
import io.reactivex.Completable
import io.reactivex.Single

class LoginRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPreferenceService: SharedPreferenceService
) : LoginRepository {
    override fun login(login: String, password: String): Single<BaseServerResponse<User>> {
        return apiService.login(login, password)
            .map { serverResponse ->
                serverResponse.objectList?.takeIf { it.isNotEmpty() }
                    ?.let { sharedPreferenceService.saveUserInfo(it[0]) }
                serverResponse
            }
    }

    override fun saveServerConfig(serverConfig: ServerConfig): Single<ServerConfig> {
        return Single.fromCallable {
            sharedPreferenceService.saveServerConfig(serverConfig)
        }.map {
            sharedPreferenceService.getServerConfig()
        }
    }

    override fun getServerConfig(): Single<ServerConfig> {
        return Single.fromCallable {
            sharedPreferenceService.getServerConfig()
        }
    }

    override fun saveLoginPass(login: String, pass: String): Single<Unit> {
        return Single.fromCallable {
            sharedPreferenceService.saveLoginPassString(AuthUtil.convertToBase64(login, pass))
        }
    }

    override fun setServiceUrl(serviceUrl: String): Completable {
        return apiService.updateServiceUrl(serviceUrl)
    }
}