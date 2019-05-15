package com.project.mobile_university.domain

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.*
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

    @GET("api/v1/check_list_students/")
    fun getCheckList(
        @Header("Authorization") loginPassString: String,
        @Query("check_list") checkListId: Long
    ): Observable<BaseServerResponse<CheckListRecord>>

    @PUT("api/v1/check_list_students/")
    fun putCheckList(
        @Header("Authorization") loginPassString: String,
        @Query("check_list") checkListId: Long,
        @Body records: List<CheckListRecord>
    ): Observable<Unit>

    @POST("api/v1/check_list/")
    fun createCheckList(
        @Header("Authorization") loginPassString: String,
        @Body lessonOwner: JsonObject
    ): Observable<Unit>

    @GET("api/v1/auth/user/logout")
    fun logout(@Header("Authorization") loginPassString: String): Observable<Nothing>
}