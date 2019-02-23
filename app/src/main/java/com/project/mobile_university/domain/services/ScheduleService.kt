package com.project.mobile_university.domain.services

import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.*

class ScheduleService(private val apiService: ApiService,
                      private val databaseService: DatabaseService,
                      private val sharedPreferenceService: SharedPreferenceService) {

    fun syncScheduleDaysForSubgroup(startDate: Date,
                                    endDate: Date,
                                    subgroupId: Long): Observable<List<ScheduleDayWithLessons>> {

        val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)

        return apiService.getScheduleOfWeekForSubgroup(startDate, endDate, subgroupId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!, datesRange)
            }
            .flatMap { databaseService.getScheduleDayListForSubgroup(datesRange, subgroupId) }
    }


    fun syncScheduleDaysForTeacher(startDate: Date,
                                   endDate: Date,
                                   teacherId: Long): Observable<List<ScheduleDayWithLessons>> {
        val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)

        return apiService.getScheduleOfWeekForTeacher(startDate, endDate, teacherId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!, datesRange)
            }
            .flatMap { databaseService.getScheduleDayListForTeacher(datesRange, teacherId) }
    }

    fun syncSchedule(): Observable<List<ScheduleDay>> {
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