package com.project.mobile_university.domain.utils

import android.util.Base64


object AuthUtil {
    fun convertToBase64(login: String, password: String): String {
        return "Basic ${Base64.encodeToString("$login:$password".toByteArray(), Base64.NO_WRAP)}"
    }
}