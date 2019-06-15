package com.project.mobile_university.domain.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.Student as GsonStudent
import com.project.mobile_university.data.gson.Teacher as GsonTeacher
import com.project.mobile_university.data.presentation.Teacher as PresentationTeacher
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.data.presentation.UserInformation
import com.project.mobile_university.domain.UniversityApi
import com.project.mobile_university.domain.mappers.*
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.DatabaseService
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*
import com.project.mobile_university.data.gson.Lesson as GsonLesson
import com.project.mobile_university.data.presentation.Lesson as PresentationLesson
import com.project.mobile_university.data.room.entity.Lesson as SqlLesson

class ScheduleRepositoryImpl(
    private val apiService: ApiService,
    private val databaseService: DatabaseService,
    private val sharedPreferenceService: SharedPreferenceService
) : ScheduleRepository {

    override fun syncScheduleDaysForSubgroup(
        startDate: Date,
        endDate: Date,
        subgroupId: Long
    ): Single<List<ScheduleDay>> {
        return apiService.getScheduleOfWeekForSubgroup(startDate, endDate, subgroupId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!.map { gsonDay -> ScheduleDayMapper.toDatabase(gsonDay) })
                    .flatMap {
                        val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)
                        databaseService.getScheduleDayListForSubgroup(datesRange, subgroupId)
                    }.map { sqlDays -> sqlDays.map { sqlDay -> ScheduleDayMapper.toPresentation(sqlDay) } }
            }
    }


    override fun syncScheduleDaysForTeacher(
        startDate: Date,
        endDate: Date,
        teacherId: Long
    ): Single<List<ScheduleDay>> {
        return apiService.getScheduleOfWeekForTeacher(startDate, endDate, teacherId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!.map { sqlDay -> ScheduleDayMapper.toDatabase(sqlDay) })
            }
            .flatMap {
                val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)
                databaseService.getScheduleDayListForTeacher(datesRange, teacherId)
            }
            .map { sqlDays -> sqlDays.map { sqlDay -> ScheduleDayMapper.toPresentation(sqlDay) } }
    }

    override fun syncSchedule(): Single<List<ScheduleDay>> {
        val (monday, sunday) = CalendarUtil.obtainMondayAndSunday(Date())

        val remoteObservable = Single
            .fromCallable { sharedPreferenceService.getUserInfo() }
            .flatMap { user ->
                return@flatMap when (user) {
                    is GsonStudent -> apiService.getScheduleOfWeekForSubgroup(monday, sunday, user.subgroupId)
                    is GsonTeacher -> apiService.getScheduleOfWeekForTeacher(monday, sunday, user.teacherId)
                }
            }
            .map { gsonDays -> gsonDays.objectList!!.map { gsonDay -> ScheduleDayMapper.toPresentation(gsonDay) } }

        val storedObservable = Single
            .fromCallable { sharedPreferenceService.getUserInfo() }
            .flatMap { user ->
                val datesRange = CalendarUtil.buildRangeBetweenDates(monday, sunday)

                return@flatMap when (user) {
                    is GsonStudent -> databaseService.getScheduleDayListForSubgroup(datesRange, user.subgroupId)
                    is GsonTeacher -> databaseService.getScheduleDayListForTeacher(datesRange, user.teacherId)
                }
            }
            .map { sqlDays -> sqlDays.map { sqlDay -> ScheduleDayMapper.toPresentation(sqlDay) } }

        return Single.zip(remoteObservable, storedObservable, diffFunction())
            .flatMap { (insertList, updatedDays) ->
                databaseService.saveScheduleDay(insertList.map { presentationDay ->
                    ScheduleDayMapper.toDatabase(
                        presentationDay
                    )
                })
                    .map { updatedDays }
            }
    }

    override fun syncLesson(lessonExtId: Long): Single<PresentationLesson> {
        return apiService.getLesson(lessonExtId)
            .flatMap { lesson ->
                databaseService.saveLessons(listOf(LessonMapper.toDatabase(lesson)))
                    .map { LessonMapper.toPresentation(lesson) }
            }
    }

    override fun getLesson(lessonExtId: Long): Single<PresentationLesson> {
        return databaseService.getLessonWithSubgroup(lessonExtId)
            .map { LessonMapper.toPresentation(it) }
    }

    override fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus): Completable {
        return Single.fromCallable {
            JsonObject().apply {
                addProperty("lesson_status", lessonStatus.identifier)
            }
        }.flatMapCompletable { body -> apiService.updateLessonStatus(lessonId, body) }
    }

    override fun getCheckList(checkListExtId: Long): Single<List<CheckListRecord>> {
        return apiService.getCheckList(checkListExtId)
            .map { CheckListMapper.gsonToPresentation(it) }
    }

    override fun putCheckList(checkListExtId: Long, records: List<CheckListRecord>): Completable {
        return Single.fromCallable { records.toJson() }
            .flatMapCompletable { gsonRecords -> apiService.putCheckList(checkListExtId, gsonRecords) }
    }

    override fun createCheckList(lessonExtId: Long): Single<PresentationLesson> {
        return apiService.createCheckList(lessonExtId)
            .andThen(syncLesson(lessonExtId))
    }

    override fun getTeachers(
        limit: Int,
        offset: Int
    ): Single<List<PresentationTeacher>> {
        return apiService.getTeachers(limit, offset)
            .map { teachers ->
                teachers.objectList!!.map { UserMapper.toPresentation(it) }
            }
    }

    override fun getUserInfo(userId: Long): Single<UserInformation> {
        return apiService.getUserInfo(userId)
            .map { userInformation ->
                UserInformationMapper.toPresentation(userInformation)
            }
    }

    override fun getScheduleDaysForTeacher(
        startDate: Date,
        endDate: Date,
        teacherId: Long,
        limit: Int,
        offset: Int
    ): Single<List<ScheduleDay>> {
        return apiService.getScheduleOfWeekForTeacher(
            startDate,
            endDate,
            teacherId,
            limit,
            offset
        ).map { scheduleDaysGson ->
            scheduleDaysGson.objectList?.map { ScheduleDayMapper.toPresentation(it) }
        }
    }

    private fun diffFunction(): BiFunction<List<ScheduleDay>, List<ScheduleDay>, Pair<List<ScheduleDay>, List<ScheduleDay>>> {
        return BiFunction { remoteList, storedList ->
            val insertedDays = remoteList.minus(storedList).toMutableList()
            val commonElements = remoteList.intersect(storedList)

            val updatedDays = mutableListOf<ScheduleDay>()

            val insertIterator = insertedDays.iterator()

            commonElements.forEach { commonElement ->
                while (insertIterator.hasNext()) {
                    val insertElement = insertIterator.next()

                    if (insertElement.extId == commonElement.extId) {
                        updatedDays.add(insertElement)
                        insertIterator.remove()
                        break
                    }
                }
            }
            return@BiFunction Pair(remoteList, updatedDays)
        }
    }

    private fun List<CheckListRecord>.toJson(): JsonObject {
        val jsonRecords = JsonArray()

        this.forEach { record ->
            jsonRecords.add(JsonObject().apply {
                addProperty("id", record.id)
                addProperty("check_list", "${UniversityApi.CHECK_LIST_PATH}${record.checkListId}/")
                addProperty("status", record.status.value)
                addProperty("student", "${UniversityApi.STUDENT_PATH}${record.studentId}/")
            })
        }

        return JsonObject().apply {
            add("objects", jsonRecords)
        }
    }
}