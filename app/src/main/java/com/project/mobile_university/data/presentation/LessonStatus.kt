package com.project.mobile_university.data.presentation

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.project.mobile_university.R

enum class LessonStatus(
    val identifier: Int,
    @StringRes val description: Int,
    @ColorRes val itemColor: Int
) {
    READY(
        0,
        R.string.lesson_status_ready_string,
        R.color.lesson_status_ready_color
    ),
    PENDING(
        1,
        R.string.lesson_status_pending_string,
        R.color.lesson_status_pending_color
    ),
    CANCELED(
        2,
        R.string.lesson_status_canceled_string,
        R.color.lesson_status_canceled_color
    );
}