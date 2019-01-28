package com.project.mobile_university.domain

import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.domain.utils.database.ScheduleUtils
import io.reactivex.Observable

class DatabaseService(private val database: UniversityDatabase) {
    fun saveScheduleDay(scheduleDay: ScheduleDay,
                        requestedDayIds: List<String>): Observable<Unit> {
        return Observable.fromCallable { ScheduleUtils.insertOrReplaceScheduleDayList(database,
            requestedDayIds, listOf(scheduleDay)) }
    }
}