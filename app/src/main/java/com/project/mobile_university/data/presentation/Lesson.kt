package com.project.mobile_university.data.presentation

import com.project.mobile_university.data.shared.AbstractLesson

data class Lesson(
    override val extId: Long,
    override val dayId: Long,
    override var currentDate: String,
    override val lectureHallName: String,
    override val lectureTypeName: String,
    override val lessonStart: String,
    override val lessonEnd: String,
    override val subgroupList: List<Subgroup>,
    override val subjectName: String,
    override val teacherName: String,
    override val teacherExtId: Long,
    override val lessonStatus: Int
) : AbstractLesson<Subgroup> {

    fun deepCopy() = Lesson(
        extId = extId,
        dayId = dayId,
        currentDate = currentDate,
        lectureHallName = lectureHallName,
        lectureTypeName = lectureTypeName,
        lessonStart = lessonStart,
        lessonEnd = lessonEnd,
        subgroupList = subgroupList,
        subjectName = subjectName,
        teacherName = teacherName,
        teacherExtId = teacherExtId,
        lessonStatus = lessonStatus
    )
}