package com.project.mobile_university.domain

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.Lesson
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import io.reactivex.Observable
import retrofit2.http.*

interface UniversityApi {
    @GET("api/v1/auth/user/")
    fun login(@Header("Authorization") loginPassString: String): Observable<BaseServerResponse<User>>

    @GET("api/v1/schedule_day/")
    fun getScheduleDayForSubgroup(
        @Header("Authorization") loginPassString: String,
        @Query("current_date") currentDate: String,
        @Query("subgroup_id") subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    @GET("api/v1/schedule_day/")
    fun getScheduleWeekForSubgroup(
        @Header("Authorization") loginPassString: String,
        @Query("range") dateRangeString: String,
        @Query("subgroup_id") subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    @GET("api/v1/schedule_day/")
    fun getScheduleWeekForTeacher(
        @Header("Authorization") loginPassString: String,
        @Query("range") dateRangeString: String,
        @Query("teacher_id") teacherId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    @GET("api/v1/schedule_cell/{id}/")
    fun getLesson(
        @Header("Authorization") loginPassString: String,
        @Path("id") lessonId: Long
    ): Observable<Lesson>

    @PATCH("api/v1/schedule_cell/{id}/")
    fun patchLessonStatus(
        @Header("Authorization") loginPassString: String,
        @Path("id") lessonId: Long,
        @Body body: JsonObject
    ): Observable<Unit>

    @GET("api/v1/auth/user/logout")
    fun logout(@Header("Authorization") loginPassString: String): Observable<Nothing>
}