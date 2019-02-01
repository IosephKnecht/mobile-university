package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.Lesson as LessonPresentation
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

    fun toPresentation(lessonGson: LessonGson): LessonPresentation {
        return with(lessonGson) {
            val subgroupPresentationList = SubgroupMapper.toPresentation(subgroupList)

            LessonPresentation(id = id,
                currentDate = currentDate,
                dayId = dayId,
                lectureHallName = lectureHallName,
                lectureTypeName = lectureTypeName,
                lessonEnd = lessonEnd,
                lessonStart = lessonStart,
                subjectName = subjectName,
                teacherName = teacherName,
                subgroupList = subgroupPresentationList)
        }
    }

    fun toPresentation(lessonGsonList: List<LessonGson>): List<LessonPresentation> {
        return lessonGsonList.map { toPresentation(it) }
    }
}