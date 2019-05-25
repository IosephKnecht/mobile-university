package com.project.mobile_university.data.presentation

import androidx.annotation.StringRes
import com.project.mobile_university.R

enum class CheckListStatus(
    val value: Int,
    @StringRes val description: Int
) {
    NOT_COME(0, R.string.not_come_status_string),
    CAME_LATE(1, R.string.came_late_status_string),
    NOT_COME_WITH_APPROVED(2, R.string.not_come_with_approved_string),
    HAS_COME(3, R.string.has_come_string)
}