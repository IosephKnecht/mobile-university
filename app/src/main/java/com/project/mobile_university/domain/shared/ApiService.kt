package com.project.mobile_university.domain.shared

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import io.reactivex.Observable
import java.util.*

interface ApiService {
    fun login(login: String, password: String): Observable<BaseServerResponse<User>>
    fun logout(): Observable<Nothing>
    fun getScheduleByDate(currentDate: Date, subgroupId: Long): Observable<BaseServerResponse<ScheduleDay>>
    fun getScheduleOfWeekForSubgroup(
        startWeek: Date,
        endWeek: Date,
        subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    fun getScheduleOfWeekForTeacher(
        startWeek: Date,
        endWeek: Date,
        teacherId: Long
    ): Observable<BaseServerResponse<ScheduleDay>>

    fun updateServiceUrl(serviceUrl: String): Observable<Unit>
    fun updateLessonStatus(lessonId: Long, body: JsonObject): Observable<Unit>
}