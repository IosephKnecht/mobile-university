package com.project.mobile_university.domain.services

import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.utils.database.ScheduleSqlUtil
import io.reactivex.Observable

class DatabaseService(private val database: UniversityDatabase) {
    fun saveScheduleDay(scheduleDayList: List<ScheduleDay>,
                        requestedDayIds: List<String>): Observable<Unit> {
        return makeReactive {
            ScheduleSqlUtil.insertOrReplaceScheduleDays(database, requestedDayIds, scheduleDayList)
        }
    }

    fun getScheduleDayListForSubgroup(datesRange: List<String>,
                                      subgroupId: Long): Observable<List<ScheduleDayWithLessons>> {
        return makeReactive {
            database.sheduleDayDao().getScheduleDayWithLessonsForSubgroup(datesRange, subgroupId)
        }
    }

    fun getScheduleDayListForTeacher(datesRange: List<String>,
                                     teacherId: Long): Observable<List<ScheduleDayWithLessons>> {
        return makeReactive {
            database.sheduleDayDao().getScheduleDayWithLessonsForTeacher(datesRange, teacherId)
        }

    }

    private fun <T> makeReactive(block: () -> T): Observable<T> {
        return Observable.fromCallable { block() }
    }
}