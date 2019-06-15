package com.project.mobile_university.presentation.schedule.host.view

import java.util.*

interface ScheduleHostListener {
    fun showLessonInfo(lessonExtId: Long)
    fun showCheckList(checkListExtId: Long)
    fun showUserInfo(userId: Long)
    fun showScheduleRange(teacherId: Long, startDate: Date, endDate: Date)
}