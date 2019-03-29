package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.AuthUtil
import io.reactivex.Observable

class LoginRepositoryImpl(private val apiService: ApiService,
                          private val sharedPreferenceService: SharedPreferenceService) : LoginRepository {
    override fun login(login: String, password: String): Observable<BaseServerResponse<User>> {
        return apiService.login(login, password)
            .map { serverResponse ->
                serverResponse.objectList?.takeIf { it.isNotEmpty() }
                    ?.let { sharedPreferenceService.saveUserInfo(it[0]) }
                serverResponse
            }
    }

    override fun saveServerConfig(serverConfig: ServerConfig): Observable<ServerConfig> {
        return Observable.fromCallable {
            sharedPreferenceService.saveServerConfig(serverConfig)
        }.map {
            sharedPreferenceService.getServerConfig()
        }
    }

    override fun getServerConfig(): Observable<ServerConfig> {
        return Observable.fromCallable {
            sharedPreferenceService.getServerConfig()
        }
    }

    override fun saveLoginPass(login: String, pass: String): Observable<Unit> {
        return Observable.fromCallable {
            sharedPreferenceService.saveLoginPassString(AuthUtil.convertToBase64(login, pass))
        }
    }

    override fun setServiceUrl(serviceUrl: String): Observable<Unit> {
        return apiService.updateServiceUrl(serviceUrl)
    }
}