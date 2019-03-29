package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import io.reactivex.Observable

interface LoginRepository {
    fun login(login: String, password: String): Observable<BaseServerResponse<User>>
    fun saveServerConfig(serverConfig: ServerConfig): Observable<ServerConfig>
    fun getServerConfig(): Observable<ServerConfig>
    fun saveLoginPass(login: String, pass: String): Observable<Unit>
    fun setServiceUrl(serviceUrl: String): Observable<Unit>
}