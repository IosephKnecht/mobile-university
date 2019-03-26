package com.project.mobile_university.data.shared

interface AbstractLesson<T : AbstractSubgroup> {
    val extId: Long
    val dayId: Long
    val currentDate: String
    val lectureHallName: String
    val lectureTypeName: String
    val lessonStart: String
    val lessonEnd: String
    val subgroupList: List<T>
    val subjectName: String
    val teacherName: String
    val teacherExtId: Long
}