package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
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

class ScheduleRepositoryImpl(private val apiService: ApiService,
                             private val databaseService: DatabaseService,
                             private val sharedPreferenceService: SharedPreferenceService) : ScheduleRepository {
    override fun syncScheduleDaysForSubgroup(startDate: Date,
                                             endDate: Date,
                                             subgroupId: Long): Observable<List<ScheduleDay>> {

        val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)

        return apiService.getScheduleOfWeekForSubgroup(startDate, endDate, subgroupId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!, datesRange)
            }
            .flatMap { databaseService.getScheduleDayListForSubgroup(datesRange, subgroupId) }
            .map { ScheduleDayMapper.toPresentation(it) }
    }


    override fun syncScheduleDaysForTeacher(startDate: Date,
                                            endDate: Date,
                                            teacherId: Long): Observable<List<ScheduleDay>> {
        val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)

        return apiService.getScheduleOfWeekForTeacher(startDate, endDate, teacherId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!, datesRange)
            }
            .flatMap { databaseService.getScheduleDayListForTeacher(datesRange, teacherId) }
            .map { ScheduleDayMapper.toPresentation(it) }
    }

    override fun syncSchedule(): Observable<List<ScheduleDay>> {
        val (monday, sunday) = CalendarUtil.obtainMondayAndSunday(Date())
        val datesRange = CalendarUtil.buildRangeBetweenDates(monday, sunday)

        val remoteObservable = Observable
            .fromCallable { sharedPreferenceService.getUserInfo() }
            .flatMap { user ->
                return@flatMap when (user) {
                    is Student -> apiService.getScheduleOfWeekForSubgroup(monday, sunday, user.subgroupId)
                    is Teacher -> apiService.getScheduleOfWeekForTeacher(monday, sunday, user.teacherId)
                }
            }
            .map { ScheduleDayMapper.toPresentation(it.objectList!!) }

        val storedObservable = Observable
            .fromCallable { sharedPreferenceService.getUserInfo() }
            .flatMap { user ->
                return@flatMap when (user) {
                    is Student -> databaseService.getScheduleDayListForSubgroup(datesRange, user.subgroupId)
                    is Teacher -> databaseService.getScheduleDayListForTeacher(datesRange, user.teacherId)
                }
            }
            .map { ScheduleDayMapper.toPresentation(it) }

        val diffObservable = Observable
            .combineLatest(remoteObservable, storedObservable, diffFunction())
            .flatMap { diff ->
                val (insertList, updatedDays) = diff
                databaseService.saveScheduleDay(ScheduleDayMapper.toGson(insertList), datesRange)
                    .map { updatedDays }
            }

        return diffObservable
    }

    override fun getLesson(lessonId: Long): Observable<Lesson> {
        return databaseService.getLessonWithSubgroup(lessonId)
            .map { LessonMapper.toPresentation(it) }
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