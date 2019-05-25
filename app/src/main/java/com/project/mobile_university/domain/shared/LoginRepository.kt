package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import io.reactivex.Completable
import io.reactivex.Single

interface LoginRepository {
    fun login(login: String, password: String): Single<BaseServerResponse<User>>
    fun saveServerConfig(serverConfig: ServerConfig): Single<ServerConfig>
    fun getServerConfig(): Single<ServerConfig>
    fun saveLoginPass(login: String, pass: String): Single<Unit>
    fun setServiceUrl(serviceUrl: String): Completable
}