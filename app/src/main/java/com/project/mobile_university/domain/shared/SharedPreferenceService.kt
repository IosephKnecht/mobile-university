package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig

interface SharedPreferenceService {
    fun saveServerConfig(serverConfig: ServerConfig)
    fun getServerConfig(): ServerConfig
    fun saveLoginPassString(loginPassString: String)
    fun removeLoginPassString()
    fun getLoginPassString(): String
    fun saveUserInfo(user: User)
    fun getUserInfo(): User
    fun removeUserInfo()
}