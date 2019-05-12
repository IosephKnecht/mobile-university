package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.gson.Student as StudentGson
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.Student as StudentPresentation
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

    fun toPresentation(student: StudentGson): StudentPresentation {
        return with(student) {
            StudentPresentation(
                email = email,
                firstName = firstName,
                lastName = lastName,
                subgroupId = subgroupId,
                groupId = groupId
            )
        }
    }

    fun toGson(student: StudentPresentation): StudentGson {
        return with(student) {
            StudentGson(
                email = email,
                firstName = firstName,
                lastName = lastName,
                groupId = groupId,
                subgroupId = subgroupId,
                isStudent = true
            )
        }
    }
}