package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.room.entity.Lesson as LessonSql
import com.project.mobile_university.data.gson.Lesson as LessonGson

object LessonMapper {
    fun toDatabase(lessonGson: LessonGson): LessonSql {
        return with(lessonGson) {
            LessonSql(currentDate = currentDate,
                    lectureHallName = lectureHallName,
                    lessonStart = lessonStart,
                    lessonEnd = lessonEnd,
                    lectureTypeName = lectureTypeName,
                    subjectName = subjectName,
                    teacherName = teacherName,
                    extId = id,
                    dayId = dayId)
        }
    }

    fun toDatabase(lessonGsonList: List<LessonGson>): List<LessonSql> {
        return lessonGsonList.map { toDatabase(it) }
    }
}