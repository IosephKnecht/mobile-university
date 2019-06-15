package com.project.mobile_university.presentation.schedule_range.view.adapter

import com.project.mobile_university.data.presentation.Lesson as PresentationLesson

class ScheduleRangeAdapter {
}

sealed class ScheduleDayViewState {
    data class Header(val date: String) : ScheduleDayViewState()

    data class Lesson(
        val lesson: PresentationLesson,
        val clickListener: (lessonId: Long) -> Unit
    ) : ScheduleDayViewState()
}