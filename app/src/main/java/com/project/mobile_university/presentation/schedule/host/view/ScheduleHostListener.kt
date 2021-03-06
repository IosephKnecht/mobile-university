package com.project.mobile_university.presentation.schedule.host.view

import java.util.*

interface ScheduleHostListener {
    fun showLessonInfo(lessonExtId: Long)
    fun editLessonInfo(lessonExtId: Long)
    fun showCheckList(checkListExtId: Long)
    fun showUserInfo(userId: Long, isMe: Boolean)
    fun showScheduleRange(teacherId: Long, startDate: Date, endDate: Date)
    fun logout()
}