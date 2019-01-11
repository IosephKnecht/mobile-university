package com.project.mobile_university.data.presentation

data class ServerConfig(val protocol: Protocol = Protocol.HTTP,
                        val serviceName: String = "127.0.0.1",
                        val port: Int = 8000) {
    override fun toString(): String {
        return "${protocol.header}$serviceName:$port"
    }
}