package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class CheckListRecord(
    @SerializedName("id")
    val id: Long,
    @SerializedName("check_list_id")
    val checkListId: Long,
    @SerializedName("status")
    val status: Int,
    @SerializedName("student_id")
    val studentId: Long,
    @SerializedName("student_first_name")
    val studentFirstName: String?,
    @SerializedName("student_last_name")
    val studentLastName: String?,
    @SerializedName("subgroup_id")
    val subgroupId: Long,
    @SerializedName("subgroup_name")
    val subgroupName: String?
)