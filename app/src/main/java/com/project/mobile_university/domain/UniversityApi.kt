package com.project.mobile_university.domain

import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UniversityApi {
    @GET("api/v1/auth/user/")
    fun login(@Header("Authorization") loginPassString: String): Observable<BaseServerResponse<User>>

    @GET("api/v1/schedule_day/")
    fun scheduleDay(@Header("Authorization") loginPassString: String,
                    @Query("current_date") currentDate: String,
                    @Query("subgroup_id") subgroupId: Long): Observable<BaseServerResponse<ScheduleDay>>
}