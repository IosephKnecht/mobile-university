package com.project.mobile_university.data.presentation

import androidx.annotation.StringRes
import com.project.mobile_university.R

enum class CheckListStatus(
    val value: Int,
    @StringRes val description: Int
) {
    NOT_COME(0, R.string.app_name),
    CAME_LATE(1, R.string.app_name),
    NOT_COME_WITH_APPROVED(2, R.string.app_name),
    HAS_COME(3, R.string.app_name)
}