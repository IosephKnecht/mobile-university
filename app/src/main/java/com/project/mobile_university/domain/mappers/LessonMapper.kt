package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.LessonType
import com.project.mobile_university.data.gson.Lesson as LessonGson
import com.project.mobile_university.data.presentation.Lesson as LessonPresentation
import com.project.mobile_university.data.room.entity.Lesson as LessonSql

object LessonMapper {
    fun toDatabase(lesson: LessonGson): LessonSql {
        return with(lesson) {
            LessonSql(
                id = extId,
                dayExtId = dayId,
                currentDate = currentDate,
                lectureHallName = lectureHallName,
                lessonStart = lessonStart,
                lessonEnd = lessonEnd,
                lessonType = lectureTypeName,
                subjectName = subjectName,
                teacherName = teacherName,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.gsonToSql(subgroupList),
                lessonStatus = lessonStatus,
                checkListExtId = checkListExtId,
                coordinates = coordinates
            )
        }
    }

    fun toDatabase(lesson: LessonPresentation): LessonSql {
        return with(lesson) {
            LessonSql(
                id = extId,
                dayExtId = dayExtId,
                currentDate = currentDate,
                lectureHallName = lectureHallName,
                lessonStart = lessonStart,
                lessonEnd = lessonEnd,
                lessonType = lessonType?.value,
                subjectName = subjectName,
                teacherName = teacherName,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.presentationToSql(subgroupList),
                lessonStatus = LessonStatusMapper.toInt(lessonStatus),
                checkListExtId = checkListExtId,
                coordinates = coordinates
            )
        }
    }

    fun toPresentation(lesson: LessonSql): LessonPresentation {
        return with(lesson) {
            LessonPresentation(
                extId = id,
                teacherName = teacherName,
                subjectName = subjectName,
                lessonType = toLessonType(lessonType),
                lessonEnd = lessonEnd,
                lessonStart = lessonStart,
                lectureHallName = lectureHallName,
                currentDate = currentDate,
                dayExtId = lesson.dayExtId,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.sqlToPresetation(subgroupList),
                lessonStatus = LessonStatusMapper.toPresentation(lessonStatus),
                checkListExtId = checkListExtId,
                coordinates = coordinates
            )
        }
    }

    fun toPresentation(lesson: LessonGson): LessonPresentation {
        return with(lesson) {
            LessonPresentation(
                extId = extId,
                teacherName = teacherName,
                subjectName = subjectName,
                lessonType = toLessonType(lectureTypeName),
                lessonStart = lessonStart,
                lessonEnd = lessonEnd,
                currentDate = currentDate,
                dayExtId = dayId,
                lectureHallName = lectureHallName,
                lessonStatus = LessonStatusMapper.toPresentation(lessonStatus),
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.gsonToPresentation(subgroupList),
                checkListExtId = checkListExtId,
                coordinates = coordinates
            )
        }
    }

    private fun toLessonType(value: Int?) = LessonType.values().find { it.value == value }

}