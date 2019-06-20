package com.project.mobile_university.data.presentation

import com.project.mobile_university.BuildConfig

data class ServerConfig(
    val protocol: Protocol = Protocol.HTTPS,
    val serviceName: String = BuildConfig.DEFAULT_URL,
    val port: Int? = null
) {
    override fun toString(): String {
        var serviceUrl = "${protocol.header}$serviceName"
        if (port != null) serviceUrl += ":$port"
        return serviceUrl
    }
}