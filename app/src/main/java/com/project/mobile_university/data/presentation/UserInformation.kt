package com.project.mobile_university.data.presentation

import androidx.annotation.StringRes
import com.project.mobile_university.R

enum class AdditionalEnum(@StringRes val description: Int) {
    SUBGROUP(R.string.subgroup_description),
    CATHEDRA(R.string.cathedra_description)
}

enum class ContactsEnum(@StringRes val description: Int) {
    EMAIL(R.string.email_description)
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
    val gender: Gender? = null,
    val additionalList: List<AdditionalModel>,
    val userContacts: List<UserContactModel>
)