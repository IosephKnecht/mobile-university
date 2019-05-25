package com.project.mobile_university.domain.shared

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.*
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface ApiService {

    fun login(login: String, password: String): Single<BaseServerResponse<User>>

    fun getScheduleByDate(currentDate: Date, subgroupId: Long): Single<BaseServerResponse<ScheduleDay>>

    fun getScheduleOfWeekForSubgroup(
        startWeek: Date,
        endWeek: Date,
        subgroupId: Long
    ): Single<BaseServerResponse<ScheduleDay>>

    fun getScheduleOfWeekForTeacher(
        startWeek: Date,
        endWeek: Date,
        teacherId: Long
    ): Single<BaseServerResponse<ScheduleDay>>

    fun updateServiceUrl(serviceUrl: String): Completable

    fun updateLessonStatus(lessonId: Long, body: JsonObject): Completable

    fun getLesson(lessonId: Long): Single<Lesson>

    fun getCheckList(checkListExtId: Long): Single<List<CheckListRecord>>

    fun putCheckList(checkListExtId: Long, records: JsonObject): Completable

    fun createCheckList(lessonId: Long): Completable
}