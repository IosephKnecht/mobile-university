package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.UserInformation as UserInformationPresentation
import com.project.mobile_university.data.gson.UserInformation as UserInformationGson

object UserInformationMapper {
    fun toPresentation(userInformation: UserInformationGson): UserInformationPresentation {
        return with(userInformation) {
            val userId = id
            val firstName = firstName
            val lastName = lastName
            val email = email
            var isUndefined = false
            var isTeacher = false
            var isStudent = false
            var subgroupId: Long? = null
            var subgroupName: String? = null
            var cathedraId: Long? = null
            var cathedraName: String? = null
            var gender: Boolean? = null

            when (userInformation) {
                is UserInformationGson.Undefined -> {
                    isUndefined = true
                }
                is UserInformationGson.Teacher -> {
                    isTeacher = true
                    cathedraId = userInformation.cathedraId
                    cathedraName = userInformation.cathedraName
                    gender = userInformation.gender
                }
                is UserInformationGson.Student -> {
                    isStudent = true
                    subgroupId = userInformation.subgroupId
                    subgroupName = userInformation.subgroupName
                    gender = userInformation.gender
                }
            }

            UserInformationPresentation(
                isUndefined = isUndefined,
                isStudent = isStudent,
                isTeacher = isTeacher,
                email = email,
                firstName = firstName,
                lastName = lastName,
                subgroupName = subgroupName,
                subgroupId = subgroupId,
                gender = gender,
                cathedraName = cathedraName,
                cathedraId = cathedraId,
                userId = userId
            )
        }
    }
}