package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
import com.project.mobile_university.data.shared.AbstractLesson
import com.project.mobile_university.data.gson.Lesson as LessonGson
import com.project.mobile_university.data.presentation.Lesson as LessonPresentation
import com.project.mobile_university.data.room.entity.Lesson as LessonSql

object LessonMapper {
    fun toDatabase(lesson: AbstractLesson<*>): LessonSql {
        return with(lesson) {
            LessonSql(
                dayId = dayId,
                currentDate = currentDate,
                lectureHallName = lectureHallName,
                lessonStart = lessonStart,
                lessonEnd = lessonEnd,
                lectureTypeName = lectureTypeName,
                subjectName = subjectName,
                teacherName = teacherName,
                teacherExtId = teacherExtId,
                extId = extId,
                subgroupList = SubgroupMapper.toDatabase(subgroupList),
                lessonStatus = lessonStatus
            )
        }
    }

    fun toPresentation(lessonWithSubgroups: LessonWithSubgroups): LessonPresentation {
        return with(lessonWithSubgroups) {
            LessonPresentation(
                extId = lesson!!.extId,
                teacherExtId = lesson!!.teacherExtId,
                currentDate = lesson!!.currentDate,
                teacherName = lesson!!.teacherName,
                subjectName = lesson!!.subjectName,
                lectureTypeName = lesson!!.lectureTypeName,
                lectureHallName = lesson!!.lectureHallName,
                lessonStart = lesson!!.lessonStart,
                lessonEnd = lesson!!.lessonEnd,
                dayId = lesson!!.dayId,
                subgroupList = SubgroupMapper.toPresentation(subgroupList),
                lessonStatus = lesson!!.lessonStatus
            )
        }
    }

    fun toPresentation(lesson: AbstractLesson<*>): LessonPresentation {
        return with(lesson) {
            LessonPresentation(
                extId = extId,
                teacherName = teacherName,
                subjectName = subjectName,
                lectureTypeName = lectureTypeName,
                lessonEnd = lessonEnd,
                lessonStart = lessonStart,
                lectureHallName = lectureHallName,
                currentDate = currentDate,
                dayId = dayId,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.toPresentation(subgroupList),
                lessonStatus = lessonStatus
            )
        }
    }

    fun toGson(lesson: AbstractLesson<*>): LessonGson {
        return with(lesson) {
            LessonGson(
                teacherExtId = teacherExtId,
                dayId = dayId,
                currentDate = currentDate,
                lectureHallName = lectureHallName,
                lessonStart = lessonStart,
                lessonEnd = lessonEnd,
                lectureTypeName = lectureTypeName,
                subjectName = subjectName,
                teacherName = teacherName,
                extId = extId,
                subgroupList = SubgroupMapper.toGson(subgroupList),
                lessonStatus = lessonStatus
            )
        }
    }

    fun <T : AbstractLesson<*>> toDatabase(lessonList: List<T>) =
        lessonList.map { toDatabase(it) }

    fun <T : AbstractLesson<*>> toPresentation(lessonList: List<T>) =
        lessonList.map { toPresentation(it) }

    fun <T : AbstractLesson<*>> toGson(lessonList: List<T>) =
        lessonList.map { toGson(it) }
}