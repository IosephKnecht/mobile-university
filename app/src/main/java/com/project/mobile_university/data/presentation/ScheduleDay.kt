package com.project.mobile_university.data.presentation

data class ScheduleDay(val extId: Long,
                       val currentDate: String,
                       val lesson: List<Lesson>)