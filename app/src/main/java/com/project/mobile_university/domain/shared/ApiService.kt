package com.project.mobile_university.domain.shared

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.*
import io.reactivex.Observable
import java.util.*

interface ApiService {
    fun login(login: String, password: String): Observable<BaseServerResponse<User>>
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

    fun getLesson(lessonId: Long): Observable<Lesson>

    fun getCheckList(checkListExtId: Long): Observable<List<CheckListRecord>>

    fun putCheckList(checkListExtId: Long, records: JsonObject): Observable<Unit>

    fun createCheckList(lessonId: Long): Observable<Unit>
}