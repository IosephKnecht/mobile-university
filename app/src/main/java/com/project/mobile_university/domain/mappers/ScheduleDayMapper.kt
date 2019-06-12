package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import com.project.mobile_university.data.room.entity.ScheduleDay as ScheduleDaySql

object ScheduleDayMapper {
    fun toDatabase(scheduleDay: ScheduleDayGson): ScheduleDaySql {
        return with(scheduleDay) {
            ScheduleDaySql(
                id = extId,
                currentDate = currentDate,
                lessons = lessons.map { LessonMapper.toDatabase(it) }
            )
        }
    }

    fun toDatabase(scheduleDay: ScheduleDayPresentation): ScheduleDaySql {
        return with(scheduleDay) {
            ScheduleDaySql(
                id = extId,
                currentDate = currentDate,
                lessons = lessons.map { LessonMapper.toDatabase(it) }
            )
        }
    }

    fun toPresentation(scheduleDayWithLessons: ScheduleDayWithLessons): ScheduleDayPresentation {
        return with(scheduleDayWithLessons) {
            ScheduleDayPresentation(
                extId = scheduleDay!!.id,
                currentDate = scheduleDay!!.currentDate,
                lessons = lessons.map { LessonMapper.toPresentation(it) }
            )
        }
    }

    fun toPresentation(scheduleDay: ScheduleDayGson): ScheduleDayPresentation {
        return with(scheduleDay) {
            ScheduleDayPresentation(
                extId = extId,
                currentDate = currentDate,
                lessons = lessons.map { LessonMapper.toPresentation(it) }
            )
        }
    }
}