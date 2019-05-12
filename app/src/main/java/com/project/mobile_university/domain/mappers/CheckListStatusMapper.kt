package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.CheckListStatus

object CheckListStatusMapper {
    fun toPresentation(value: Int) = CheckListStatus.values().find { it.value == value }

    fun toValue(checkListStatus: CheckListStatus) = checkListStatus.value
}