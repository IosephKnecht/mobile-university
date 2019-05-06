package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import com.project.mobile_university.data.room.entity.ScheduleDay as ScheduleDaySql

object ScheduleDayMapper {
    fun toDatabase(scheduleDay: ScheduleDayGson): ScheduleDaySql {
        return with(scheduleDay) {
            ScheduleDaySql(
                extId = extId,
                currentDate = currentDate,
                lessons = LessonMapper.gsonToDatabase(lessons)
            )
        }
    }

    fun toDatabase(scheduleDay: ScheduleDayPresentation): ScheduleDaySql {
        return with(scheduleDay) {
            ScheduleDaySql(
                extId = extId,
                currentDate = currentDate,
                lessons = LessonMapper.presentationToDatabase(lessons)
            )
        }
    }

    fun toPresentation(scheduleDayWithLessons: ScheduleDayWithLessons): ScheduleDayPresentation {
        return with(scheduleDayWithLessons) {
            ScheduleDayPresentation(
                extId = scheduleDay!!.extId,
                currentDate = scheduleDay!!.currentDate,
                lessons = LessonMapper.sqlToPresentation(lessons)
            )
        }
    }

    fun toPresentation(scheduleDay: ScheduleDayGson): ScheduleDayPresentation {
        return with(scheduleDay) {
            ScheduleDayPresentation(
                extId = extId,
                currentDate = currentDate,
                lessons = LessonMapper.gsonToPresentation(lessons)
            )
        }
    }

    fun gsonToSql(list: List<ScheduleDayGson>) = list.map { ScheduleDayMapper.toDatabase(it) }

    fun presentationToSql(list: List<ScheduleDayPresentation>) = list.map { ScheduleDayMapper.toDatabase(it) }

    fun sqlToPresentation(list: List<ScheduleDayWithLessons>) = list.map { ScheduleDayMapper.toPresentation(it) }

    fun gsonToPresentation(list: List<ScheduleDayGson>) = list.map { ScheduleDayMapper.toPresentation(it) }
}