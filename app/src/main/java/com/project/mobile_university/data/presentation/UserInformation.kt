package com.project.mobile_university.data.presentation

enum class AdditionalEnum {
    SUBGROUP, CATHEDRA
}

enum class ContactsEnum {
    EMAIL
}

data class UserInformation(
    val isUndefined: Boolean,
    val isStudent: Boolean,
    val isTeacher: Boolean,
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val subgroupId: Long? = null,
    val cathedraId: Long? = null,
    val gender: Boolean? = null,
    val additionalList: List<AdditionalModel>,
    val userContacts: List<UserContactModel>
)