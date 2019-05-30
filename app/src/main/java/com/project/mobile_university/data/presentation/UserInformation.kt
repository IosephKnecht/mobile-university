package com.project.mobile_university.data.presentation

data class UserInformation(
    val isUndefined: Boolean,
    val isStudent: Boolean,
    val isTeacher: Boolean,
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val subgroupId: Long? = null,
    val subgroupName: String? = null,
    val cathedraId: Long? = null,
    val cathedraName: String? = null,
    val gender: Boolean? = null
)