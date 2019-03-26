package com.project.mobile_university.data.shared

interface AbstractScheduleDay<T : AbstractLesson<*>> {
    val extId: Long
    val currentDate: String
    val lessons: List<T>
}