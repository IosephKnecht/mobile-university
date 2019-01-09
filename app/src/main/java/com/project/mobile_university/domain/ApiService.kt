package com.project.mobile_university.domain

import com.project.mobile_university.data.BaseServerResponse
import com.project.mobile_university.data.User
import com.project.mobile_university.domain.utils.AuthUtil
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ApiService(private val universityApi: UniversityApi) {
    lateinit var serviceUrl: String

    fun login(login: String, password: String): Observable<BaseServerResponse<User>> {
        val authString = AuthUtil.converToBase64(login, password)

        return universityApi.login(serviceUrl, authString)
            .subscribeOn(Schedulers.io())
    }
}