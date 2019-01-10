package com.project.mobile_university.domain

import com.project.mobile_university.data.BaseServerResponse
import com.project.mobile_university.data.User
import com.project.mobile_university.domain.utils.AuthUtil
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    lateinit var universityApi: UniversityApi

    var serviceUrl: String? = null
        set(value) {
            field = value

            if (value != null && value.isNotEmpty()) {
                universityApi = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(value)
                    .build()
                    .create(UniversityApi::class.java)
            }
        }

    fun login(login: String, password: String): Observable<BaseServerResponse<User>> {
        val authString = AuthUtil.convertToBase64(login, password)

        return universityApi.login(authString)
            .subscribeOn(Schedulers.io())
    }
}