package com.project.mobile_university.domain.mappers

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
                lectureTypeName = lectureTypeName,
                subjectName = subjectName,
                teacherName = teacherName,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.gsonToSql(subgroupList),
                lessonStatus = lessonStatus,
                checkListExtId = checkListExtId
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
                lectureTypeName = lectureTypeName,
                subjectName = subjectName,
                teacherName = teacherName,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.presentationToSql(subgroupList),
                lessonStatus = LessonStatusMapper.toInt(lessonStatus),
                checkListExtId = checkListExtId
            )
        }
    }

    fun toPresentation(lesson: LessonSql): LessonPresentation {
        return with(lesson) {
            LessonPresentation(
                extId = id,
                teacherName = teacherName,
                subjectName = subjectName,
                lectureTypeName = lectureTypeName,
                lessonEnd = lessonEnd,
                lessonStart = lessonStart,
                lectureHallName = lectureHallName,
                currentDate = currentDate,
                dayExtId = lesson.dayExtId,
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.sqlToPresetation(subgroupList),
                lessonStatus = LessonStatusMapper.toPresentation(lessonStatus),
                checkListExtId = checkListExtId
            )
        }
    }

    fun toPresentation(lesson: LessonGson): LessonPresentation {
        return with(lesson) {
            LessonPresentation(
                extId = extId,
                teacherName = teacherName,
                subjectName = subjectName,
                lectureTypeName = lectureTypeName,
                lessonStart = lessonStart,
                lessonEnd = lessonEnd,
                currentDate = currentDate,
                dayExtId = dayId,
                lectureHallName = lectureHallName,
                lessonStatus = LessonStatusMapper.toPresentation(lessonStatus),
                teacherExtId = teacherExtId,
                subgroupList = SubgroupMapper.gsonToPresentation(subgroupList),
                checkListExtId = checkListExtId
            )
        }
    }

    fun gsonToDatabase(list: List<LessonGson>) = list.map { LessonMapper.toDatabase(it) }

    fun presentationToDatabase(list: List<LessonPresentation>) = list.map { LessonMapper.toDatabase(it) }

    fun sqlToPresentation(list: List<LessonSql>) = list.map { LessonMapper.toPresentation(it) }

    fun gsonToPresentation(list: List<LessonGson>) = list.map { LessonMapper.toPresentation(it) }
}