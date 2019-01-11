package com.project.mobile_university.domain

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mobile_university.data.presentation.ServerConfig

class SharedPreferenceService(private val context: Context,
                              private val gson: Gson) {
    private val PREF_FILE_KEY = "mobile_university_shared_pref"
    private val SERVER_CONFIG_PREF_KEY = "server_config_key"

    private val sharedPrefFile by lazy {
        context.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun saveServerConfig(serverConfig: ServerConfig) {
        val typeToken = object : TypeToken<ServerConfig>() {}.type
        val gsonString = gson.toJson(serverConfig, typeToken)

        sharedPrefFile.edit()
            .putString(SERVER_CONFIG_PREF_KEY, gsonString)
            .apply()
    }

    fun getServerConfig(): ServerConfig {
        val typeToken = object : TypeToken<ServerConfig>() {}.type
        val serverConfigString = sharedPrefFile
            .getString(SERVER_CONFIG_PREF_KEY, null)

        return if (serverConfigString == null) {
            ServerConfig()
        } else {
            gson.fromJson<ServerConfig>(serverConfigString, typeToken)
        }
    }
}