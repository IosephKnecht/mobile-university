package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

sealed class UserInformation(
    @SerializedName("id")
    open val id: Long,
    @SerializedName("first_name")
    open val firstName: String,
    @SerializedName("last_name")
    open val lastName: String,
    @SerializedName("email")
    open val email: String,
    @SerializedName("user_type")
    open val userType: String
) {
    data class Undefined(
        override val id: Long,
        override val firstName: String,
        override val lastName: String,
        override val email: String,
        override val userType: String
    ) : UserInformation(id, firstName, lastName, email, userType)

    data class Student(
        override val id: Long,
        override val firstName: String,
        override val lastName: String,
        override val email: String,
        override val userType: String,
        @SerializedName("subgroup_id")
        val subgroupId: Long,
        @SerializedName("subgroup_name")
        val subgroupName: String,
        @SerializedName("gender")
        val gender: Int
    ) : UserInformation(id, firstName, lastName, email, userType)

    data class Teacher(
        override val id: Long,
        override val firstName: String,
        override val lastName: String,
        override val email: String,
        override val userType: String,
        @SerializedName("cathedra_id")
        val cathedraId: Long,
        @SerializedName("cathedra_name")
        val cathedraName: String,
        @SerializedName("gender")
        val gender: Int
    ) : UserInformation(id, firstName, lastName, email, userType)
}