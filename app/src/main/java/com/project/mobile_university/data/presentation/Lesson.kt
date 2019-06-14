package com.project.mobile_university.data.presentation

data class Lesson(
    val extId: Long,
    val dayExtId: Long,
    // FIXME: change on val when will be abandonment mock
    var currentDate: String,
    val lectureHallName: String,
    val lessonType: LessonType?,
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
        lessonType = lessonType,
        lessonStart = lessonStart,
        lessonEnd = lessonEnd,
        subgroupList = subgroupList,
        subjectName = subjectName,
        teacherName = teacherName,
        teacherExtId = teacherExtId,
        lessonStatus = lessonStatus,
        checkListExtId = checkListExtId
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lesson

        if (extId != other.extId) return false
        if (dayExtId != other.dayExtId) return false
        if (currentDate != other.currentDate) return false
        if (lectureHallName != other.lectureHallName) return false
        if (lessonType != other.lessonType) return false
        if (lessonStart != other.lessonStart) return false
        if (lessonEnd != other.lessonEnd) return false
        if (subjectName != other.subjectName) return false
        if (teacherName != other.teacherName) return false
        if (teacherExtId != other.teacherExtId) return false
        if (lessonStatus != other.lessonStatus) return false
        if (checkListExtId != other.checkListExtId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = extId.hashCode()
        result = 31 * result + dayExtId.hashCode()
        result = 31 * result + currentDate.hashCode()
        result = 31 * result + lectureHallName.hashCode()
        result = 31 * result + (lessonType?.hashCode() ?: 0)
        result = 31 * result + lessonStart.hashCode()
        result = 31 * result + lessonEnd.hashCode()
        result = 31 * result + subjectName.hashCode()
        result = 31 * result + teacherName.hashCode()
        result = 31 * result + teacherExtId.hashCode()
        result = 31 * result + (lessonStatus?.hashCode() ?: 0)
        result = 31 * result + (checkListExtId?.hashCode() ?: 0)
        return result
    }
}