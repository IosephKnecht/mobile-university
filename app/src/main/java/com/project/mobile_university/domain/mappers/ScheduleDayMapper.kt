package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.room.entity.ScheduleDay as ScheduleDaySql
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson

object ScheduleDayMapper {
    fun toDatabase(scheduleDayGson: ScheduleDayGson): ScheduleDaySql {
        return with(scheduleDayGson) {
            ScheduleDaySql(currentDate = currentDate)
        }
    }

    fun toDatabase(scheduleDayGsonList: List<ScheduleDayGson>): List<ScheduleDaySql> {
        return scheduleDayGsonList.map { toDatabase(it) }
    }
}