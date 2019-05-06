package com.project.mobile_university.data.presentation

data class ScheduleDay(
    val extId: Long,
    var currentDate: String,
    val lessons: List<Lesson>
)