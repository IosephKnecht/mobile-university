package com.project.mobile_university.domain

import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.domain.utils.database.ScheduleUtils
import io.reactivex.Observable

class DatabaseService(private val database: UniversityDatabase) {
    fun saveScheduleDay(scheduleDayList: List<ScheduleDay>,
                        requestedDayIds: List<String>): Observable<Unit> {
        return Observable.fromCallable {
            ScheduleUtils.insertOrReplaceScheduleDayList(database,
                requestedDayIds, scheduleDayList)
        }
    }

    fun getScheduleDayListForSubgroup(datesRange: List<String>,
                                      subgroupId: Long): Observable<List<ScheduleDayWithLessons>> {
        return Observable.fromCallable {
            database.sheduleDayDao()
                .getScheduleDayWithLessonsForSubgroup(datesRange, subgroupId)
        }
    }
}