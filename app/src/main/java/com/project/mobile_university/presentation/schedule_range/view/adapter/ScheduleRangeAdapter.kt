package com.project.mobile_university.presentation.schedule_range.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter
import com.project.mobile_university.data.presentation.Lesson as PresentationLesson

class ScheduleRangeAdapter : ViewModelAdapter() {
    init {
        cell<ScheduleDayViewState.Header>(
            layoutId = R.layout.item_range_header,
            bindingId = BR.viewState
        )

        cell<ScheduleDayViewState.Lesson>(
            layoutId = R.layout.item_range_lesson,
            bindingId = BR.viewState
        )
    }
}

sealed class ScheduleDayViewState {
    data class Header(val date: String) : ScheduleDayViewState()

    data class Lesson(
        val lesson: PresentationLesson,
        val clickListener: (lessonId: Long) -> Unit
    ) : ScheduleDayViewState()
}