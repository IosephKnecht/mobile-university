package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.data.presentation.UserInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LoginRepository {
    val loginState: Observable<Boolean>

    fun login(login: String, password: String): Single<User>
    fun logout(): Completable
    fun saveServerConfig(serverConfig: ServerConfig): Single<ServerConfig>
    fun getServerConfig(): Single<ServerConfig>
    fun saveLoginPass(login: String, pass: String): Completable
    fun setServiceUrl(serviceUrl: String): Completable
    fun getUserInfo(): Single<UserInfo>
}