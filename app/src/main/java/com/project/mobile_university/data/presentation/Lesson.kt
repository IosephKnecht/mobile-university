package com.project.mobile_university.data.presentation

data class Lesson(
    val extId: Long,
    val dayExtId: Long,
    // FIXME: change on val when will be abandonment mock
    var currentDate: String,
    val lectureHallName: String,
    val lectureTypeName: String,
    val lessonStart: String,
    val lessonEnd: String,
    val subgroupList: List<Subgroup>,
    val subjectName: String,
    val teacherName: String,
    val teacherExtId: Long,
    val lessonStatus: LessonStatus?,
    val checkListExtId: Long?
) {

    fun deepCopy() = Lesson(
        extId = extId,
        dayExtId = dayExtId,
        currentDate = currentDate,
        lectureHallName = lectureHallName,
        lectureTypeName = lectureTypeName,
        lessonStart = lessonStart,
        lessonEnd = lessonEnd,
        subgroupList = subgroupList,
        subjectName = subjectName,
        teacherName = teacherName,
        teacherExtId = teacherExtId,
        lessonStatus = lessonStatus,
        checkListExtId = checkListExtId
    )
}