package com.project.mobile_university.domain.services

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.shared.SharedPreferenceService

class SharedPreferenceServiceImpl(private val context: Context,
                                  private val gson: Gson) : SharedPreferenceService {
    private val PREF_FILE_KEY = "mobile_university_shared_pref"
    private val SERVER_CONFIG_PREF_KEY = "server_config_key"
    private val LOGIN_PASS_STRING = "login_pass_string"
    private val USER_INFO_KEY = "login_user_info"

    private val sharedPrefFile by lazy {
        context.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)
    }

    override fun saveServerConfig(serverConfig: ServerConfig) {
        val typeToken = object : TypeToken<ServerConfig>() {}.type
        val gsonString = gson.toJson(serverConfig, typeToken)

        sharedPrefFile.edit()
            .putString(SERVER_CONFIG_PREF_KEY, gsonString)
            .apply()
    }

    override fun getServerConfig(): ServerConfig {
        val typeToken = object : TypeToken<ServerConfig>() {}.type
        val serverConfigString = sharedPrefFile
            .getString(SERVER_CONFIG_PREF_KEY, null)

        return if (serverConfigString == null) {
            ServerConfig()
        } else {
            gson.fromJson<ServerConfig>(serverConfigString, typeToken)
        }
    }

    override fun saveLoginPassString(loginPassString: String) {
        sharedPrefFile.edit()
            .putString(LOGIN_PASS_STRING, loginPassString)
            .apply()
    }

    override fun removeLoginPassString() {
        sharedPrefFile.edit()
            .remove(LOGIN_PASS_STRING)
            .apply()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun getLoginPassString(): String {
        return sharedPrefFile.getString(LOGIN_PASS_STRING, "")
    }

    override fun saveUserInfo(user: User) {
        val typeToken = object : TypeToken<User>() {}.type
        val userString = gson.toJson(user, typeToken)

        sharedPrefFile.edit()
            .putString(USER_INFO_KEY, userString)
            .apply()
    }

    override fun getUserInfo(): User {
        val userInfoString = sharedPrefFile.getString(USER_INFO_KEY, "")
        val typeToken = object : TypeToken<User>() {}.type

        return gson.fromJson<User>(userInfoString, typeToken)
    }

    override fun removeUserInfo() {
        sharedPrefFile.edit()
            .remove(USER_INFO_KEY)
            .apply()
    }
}