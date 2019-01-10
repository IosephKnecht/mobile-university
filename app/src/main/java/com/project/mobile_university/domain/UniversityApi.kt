package com.project.mobile_university.domain

import com.project.mobile_university.data.BaseServerResponse
import com.project.mobile_university.data.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface UniversityApi {
    @GET("api/v1/auth/user/")
    fun login(@Header("Authorization") loginPassString: String): Observable<BaseServerResponse<User>>
}