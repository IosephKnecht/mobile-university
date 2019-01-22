package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

abstract class User(@SerializedName("email") open val email: String?,
                    @SerializedName("first_name") open val firstName: String?,
                    @SerializedName("last_name") open val lastName: String?)

class Student(override val email: String?,
              override val firstName: String?,
              override val lastName: String?,
              @SerializedName("is_student") val isStudent: Boolean,
              @SerializedName("subgroup") val groupId: Long) : User(email, firstName, lastName)