package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.LessonStatus

object LessonStatusMapper {
    fun toPresentation(value: Int) = LessonStatus.values().find { it.identifier == value }

    fun toInt(lessonStatus: LessonStatus?) = lessonStatus?.identifier ?: -1
}