package com.project.mobile_university.data.presentation

data class CheckListRecord(
    val id: Long,
    val checkListId: Long,
    val status: CheckListStatus,
    val studentId: Long,
    val studentFirstName: String?,
    val studentLastName: String?,
    val subgroupId: Long,
    val subgroupName: String?
)