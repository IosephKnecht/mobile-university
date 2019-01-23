package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

sealed class User(@SerializedName("email") open val email: String?,
                  @SerializedName("first_name") open val firstName: String?,
                  @SerializedName("last_name") open val lastName: String?)

class Student(override val email: String?,
              override val firstName: String?,
              override val lastName: String?,
              @SerializedName("is_student") val isStudent: Boolean,
              @SerializedName("group_id") val groupId: Long,
              @SerializedName("subgroup_id") val subgroupId: Long) : User(email, firstName, lastName)