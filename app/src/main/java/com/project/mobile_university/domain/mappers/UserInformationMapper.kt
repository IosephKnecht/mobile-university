package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.*
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
            var gender: Int? = null

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

            val additionalList = mutableListOf<AdditionalModel>().apply {
                subgroupName?.let { add(AdditionalModel(AdditionalEnum.SUBGROUP, it)) }
                cathedraName?.let { add(AdditionalModel(AdditionalEnum.CATHEDRA, it)) }
            }

            val userContacts = mutableListOf<UserContactModel>().apply {
                email.takeIf { it.isNotBlank() }
                    ?.let {
                        add(
                            UserContactModel(
                                contactType = ContactsEnum.EMAIL,
                                value = it
                            )
                        )
                    }
            }

            UserInformationPresentation(
                isUndefined = isUndefined,
                isStudent = isStudent,
                isTeacher = isTeacher,
                email = email,
                firstName = firstName,
                lastName = lastName,
                additionalList = additionalList,
                subgroupId = subgroupId,
                gender = toGender(gender),
                userContacts = userContacts,
                cathedraId = cathedraId,
                userId = userId
            )
        }
    }

    private fun toGender(value: Int?) = Gender.values().find { it.value == value }
}