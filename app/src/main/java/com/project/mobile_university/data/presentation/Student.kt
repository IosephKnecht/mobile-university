package com.project.mobile_university.data.presentation

data class Student(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val groupId: Long,
    val subgroupId: Long
) {
    override fun toString() = "$firstName $lastName"
}