package com.project.mobile_university.domain

import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import java.util.*

class ScheduleService(private val apiService: ApiService,
                      private val databaseService: DatabaseService) {

    fun syncScheduleDaysForSubgroup(startDate: Date,
                                    endDate: Date,
                                    subgroupId: Long): Observable<List<ScheduleDayWithLessons>> {

        val datesRange = CalendarUtil.buildRangeBetweenDates(startDate, endDate)

        return apiService.getScheduleOfWeekForSubgroup(startDate, endDate, subgroupId)
            .flatMap {
                databaseService.saveScheduleDay(it.objectList!!, datesRange)
            }
            .flatMap { databaseService.getScheduleDayListForSubgroup(datesRange, subgroupId)  }
    }
}