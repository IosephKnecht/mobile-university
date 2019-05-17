package com.project.mobile_university.domain

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.*
import io.reactivex.Observable
import retrofit2.http.*

interface UniversityApi {

    companion object {
        const val AUTH_PATH = "api/v1/auth/user/"
        const val SCHEDULE_DAY_PATH = "api/v1/schedule_day/"
        const val SCHEDULE_CELL_PATH = "api/v1/schedule_cell/"
        const val CHECK_LIST_PATH = "api/v1/check_list/"
        const val CHECK_LIST_STUDENTS_PATH = "api/v1/check_list_students/"
        const val STUDENT_PATH = "api/v1/student/"
    }


    @GET(AUTH_PATH)
    fun login(@Header("Authorization") loginPassString: String): Observable<BaseServerResponse<User>>

    @GET(SCHEDULE_DAY_PATH)
    fun getScheduleDayForSubgroup(
        @Header("Authorization") loginPassString: String,
        @Query("current_date") currentDate: String,
        @Query("subgroup_id") subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    @GET(SCHEDULE_DAY_PATH)
    fun getScheduleWeekForSubgroup(
        @Header("Authorization") loginPassString: String,
        @Query("range") dateRangeString: String,
        @Query("subgroup_id") subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    @GET(SCHEDULE_DAY_PATH)
    fun getScheduleWeekForTeacher(
        @Header("Authorization") loginPassString: String,
        @Query("range") dateRangeString: String,
        @Query("teacher_id") teacherId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    @GET("$SCHEDULE_CELL_PATH{id}/")
    fun getLesson(
        @Header("Authorization") loginPassString: String,
        @Path("id") lessonId: Long
    ): Observable<Lesson>

    @PATCH("$SCHEDULE_CELL_PATH{id}/")
    fun patchLessonStatus(
        @Header("Authorization") loginPassString: String,
        @Path("id") lessonId: Long,
        @Body body: JsonObject
    ): Observable<Unit>

    @GET(CHECK_LIST_STUDENTS_PATH)
    fun getCheckList(
        @Header("Authorization") loginPassString: String,
        @Query("check_list") checkListId: Long
    ): Observable<BaseServerResponse<CheckListRecord>>

    @PUT(CHECK_LIST_STUDENTS_PATH)
    fun putCheckList(
        @Header("Authorization") loginPassString: String,
        @Query("check_list") checkListId: Long,
        @Body records: JsonObject
    ): Observable<Unit>

    @POST(CHECK_LIST_PATH)
    fun createCheckList(
        @Header("Authorization") loginPassString: String,
        @Body lessonOwner: JsonObject
    ): Observable<Unit>
}