package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.UserInfo

object UserMapper {
    fun toPresentation(user: User): UserInfo {
        return with(user) {
            UserInfo(
                firstName = firstName ?: "",
                lastName = lastName ?: ""
            )
        }
    }
}