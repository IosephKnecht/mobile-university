package com.project.mobile_university.domain.repository

import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.presentation.Lesson as PresentationLesson
import com.project.mobile_university.data.room.entity.Lesson as SqlLesson
import com.project.mobile_university.data.gson.Lesson as GsonLesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.mappers.LessonMapper
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.DatabaseService
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.*

class ScheduleRepositoryImpl(
    private val apiService: ApiService,
    private val databaseService: DatabaseService,
    private val sharedPreferenceService: SharedPreferenceService
) : ScheduleRepository {
    override fun syncScheduleDaysForSubgroup(
        startDate: Date,
        endDate: Date,
        subgroupId: Long
    ): Observable<List<ScheduleDay>> {

        return apiService.getScheduleOfWeekForSubgroup(startDate, endDate, subgroupId)
            .flatMap {
                databaseService.saveScheduleDay(ScheduleDayMapper.gsonToSql(it.objectList!!))
            }
            .flatMap {
                val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)
                databaseService.getScheduleDayListForSubgroup(datesRange, subgroupId)
            }
            .map { ScheduleDayMapper.sqlToPresentation(it) }
    }


    override fun syncScheduleDaysForTeacher(
        startDate: Date,
        endDate: Date,
        teacherId: Long
    ): Observable<List<ScheduleDay>> {
        return apiService.getScheduleOfWeekForTeacher(startDate, endDate, teacherId)
            .flatMap {
                databaseService.saveScheduleDay(ScheduleDayMapper.gsonToSql(it.objectList!!))
            }
            .flatMap {
                val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)
                databaseService.getScheduleDayListForTeacher(datesRange, teacherId)
            }
            .map { ScheduleDayMapper.sqlToPresentation(it) }
    }

    override fun syncSchedule(): Observable<List<ScheduleDay>> {
        val (monday, sunday) = CalendarUtil.obtainMondayAndSunday(Date())

        val remoteObservable = Observable
            .fromCallable { sharedPreferenceService.getUserInfo() }
            .flatMap { user ->
                return@flatMap when (user) {
                    is Student -> apiService.getScheduleOfWeekForSubgroup(monday, sunday, user.subgroupId)
                    is Teacher -> apiService.getScheduleOfWeekForTeacher(monday, sunday, user.teacherId)
                }
            }
            .map { ScheduleDayMapper.gsonToPresentation(it.objectList!!) }

        val storedObservable = Observable
            .fromCallable { sharedPreferenceService.getUserInfo() }
            .flatMap { user ->
                val datesRange = CalendarUtil.buildRangeBetweenDates(monday, sunday)

                return@flatMap when (user) {
                    is Student -> databaseService.getScheduleDayListForSubgroup(datesRange, user.subgroupId)
                    is Teacher -> databaseService.getScheduleDayListForTeacher(datesRange, user.teacherId)
                }
            }
            .map { ScheduleDayMapper.sqlToPresentation(it) }

        val diffObservable = Observable
            .combineLatest(remoteObservable, storedObservable, diffFunction())
            .flatMap { diff ->
                val (insertList, updatedDays) = diff
                databaseService.saveScheduleDay(ScheduleDayMapper.presentationToSql(insertList))
                    .map { updatedDays }
            }

        return diffObservable
    }

    override fun syncLesson(lessonExtId: Long): Observable<PresentationLesson> {
        return Observable.zip(
            apiService.getLesson(lessonExtId),
            databaseService.getLessonByExtId(lessonExtId),
            BiFunction<GsonLesson, SqlLesson, Pair<GsonLesson, SqlLesson>> { gsonLesson, sqlLesson ->
                Pair(gsonLesson, sqlLesson)
            }
        ).flatMap { (gsonLesson, sqlLesson) ->
            databaseService.deleteRelationsForLesson(sqlLesson.extId)
                .flatMap {
                    databaseService.saveLesson(LessonMapper.toDatabase(gsonLesson))
                        .map { LessonMapper.toPresentation(gsonLesson) }
                }
        }
    }

    override fun getLesson(lessonExtId: Long): Observable<PresentationLesson> {
        return databaseService.getLessonWithSubgroup(lessonExtId)
            .map { LessonMapper.toPresentation(it) }
    }

    override fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus): Observable<Unit> {
        return Observable.just(
            JsonObject().apply {
                addProperty("lesson_status", lessonStatus.identifier)
            })
            .flatMap { body -> apiService.updateLessonStatus(lessonId, body) }
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
}