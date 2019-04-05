package com.project.mobile_university.data.presentation

import com.project.mobile_university.data.shared.AbstractScheduleDay

data class ScheduleDay(override val extId: Long,
                       override var currentDate: String,
                       override val lessons: List<Lesson>):AbstractScheduleDay<Lesson>