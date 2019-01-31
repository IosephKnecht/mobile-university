package com.project.mobile_university.domain.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class LogJsonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        val stringRowJson = response.body()?.string()

        Log.d("SERVER_RESPONSE", "server response: $stringRowJson")

        return response.newBuilder()
            .body(ResponseBody.create(response.body()?.contentType(), stringRowJson
                ?: "empty response")).build()
    }
}