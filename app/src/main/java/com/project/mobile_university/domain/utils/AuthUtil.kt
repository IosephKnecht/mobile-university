package com.project.mobile_university.domain.utils

import android.util.Base64


object AuthUtil {
    fun converToBase64(login: String, password: String): String {
        return Base64.encodeToString("Basic $login:$password".toByteArray(), Base64.NO_WRAP)
    }
}