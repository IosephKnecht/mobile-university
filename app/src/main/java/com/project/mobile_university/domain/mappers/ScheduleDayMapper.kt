package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import com.project.mobile_university.data.room.entity.ScheduleDay as ScheduleDaySql
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson

object ScheduleDayMapper {
    fun toDatabase(scheduleDayGson: ScheduleDayGson): ScheduleDaySql {
        return with(scheduleDayGson) {
            ScheduleDaySql(currentDate = currentDate,
                extId = this.id,
                lessons = LessonMapper.toDatabase(scheduleDayGson.lessons))
        }
    }

    fun toDatabase(scheduleDayGsonList: List<ScheduleDayGson>): List<ScheduleDaySql> {
        return scheduleDayGsonList.map { toDatabase(it) }
    }

    fun toPresentation(scheduleDayGson: ScheduleDayGson): ScheduleDayPresentation {
        return with(scheduleDayGson) {
            val lessonPresentationList = LessonMapper.toPresentation(lessons)
            ScheduleDayPresentation(currentDate = currentDate,
                lesson = lessonPresentationList,
                extId = id)
        }
    }

    fun toPresentation(scheduleDayGsonList: List<ScheduleDayGson>): List<ScheduleDayPresentation> {
        return scheduleDayGsonList.map { toPresentation(it) }
    }

    fun sqlToPresentation(scheduleDayWithLessons: ScheduleDayWithLessons): ScheduleDayPresentation {
        return with(scheduleDayWithLessons) {
            ScheduleDayPresentation(currentDate = this.currentDate,
                lesson = LessonMapper.sqlToPresentation(this.lessons),
                extId = extId)
        }
    }

    fun sqlToPresentation(scheduleDayListWithLessons: List<ScheduleDayWithLessons>): List<ScheduleDayPresentation> {
        return scheduleDayListWithLessons.map { sqlToPresentation(it) }
    }

    fun toGson(scheduleDay: ScheduleDayPresentation): ScheduleDayGson {
        return with(scheduleDay) {
            ScheduleDayGson(
                id = extId,
                currentDate = currentDate,
                lessons = LessonMapper.toGson(lesson))
        }
    }

    fun toGson(scheduleDays: List<ScheduleDayPresentation>): List<ScheduleDayGson> {
        return scheduleDays.map { toGson(it) }
    }
}