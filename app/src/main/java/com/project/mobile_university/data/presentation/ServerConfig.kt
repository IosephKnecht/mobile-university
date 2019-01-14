package com.project.mobile_university.data.presentation

data class ServerConfig(
    val protocol: Protocol = Protocol.HTTP,
    val serviceName: String = "127.0.0.1",
    val port: Int? = null
) {
    override fun toString(): String {
        var serviceUrl = "${protocol.header}$serviceName"
        if (port != null) serviceUrl += ":$port"
        return serviceUrl
    }
}