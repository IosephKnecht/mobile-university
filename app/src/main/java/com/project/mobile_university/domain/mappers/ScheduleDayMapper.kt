package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.shared.AbstractScheduleDay
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import com.project.mobile_university.data.room.entity.ScheduleDay as ScheduleDaySql

object ScheduleDayMapper {
    fun toDatabase(scheduleDay: AbstractScheduleDay<*>): ScheduleDaySql {
        return with(scheduleDay) {
            ScheduleDaySql(
                extId = extId,
                currentDate = currentDate,
                lessons = LessonMapper.toDatabase(lessons))
        }
    }

    fun toPresentation(scheduleDay: AbstractScheduleDay<*>): ScheduleDayPresentation {
        return with(scheduleDay) {
            ScheduleDayPresentation(
                extId = extId,
                currentDate = currentDate,
                lessons = LessonMapper.toPresentation(lessons)
            )
        }
    }

    fun toGson(scheduleDay: AbstractScheduleDay<*>): ScheduleDayGson {
        return with(scheduleDay) {
            ScheduleDayGson(
                extId = extId,
                currentDate = currentDate,
                lessons = LessonMapper.toGson(lessons))
        }
    }

    fun <T : AbstractScheduleDay<*>> toDatabase(scheduleDayList: List<T>) =
        scheduleDayList.map { toDatabase(it) }

    fun <T : AbstractScheduleDay<*>> toPresentation(scheduleDayList: List<T>) =
        scheduleDayList.map { toPresentation(it) }

    fun <T : AbstractScheduleDay<*>> toGson(scheduleDayList: List<T>) =
        scheduleDayList.map { toGson(it) }
}