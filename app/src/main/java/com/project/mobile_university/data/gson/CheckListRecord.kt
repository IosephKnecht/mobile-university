package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class CheckListRecord(
    @SerializedName("id")
    val id: Long,
    @SerializedName("student")
    val student: Student,
    @SerializedName("status")
    val status: Int
)