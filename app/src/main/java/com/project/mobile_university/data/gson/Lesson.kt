package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.shared.AbstractLesson

data class Lesson(
    @SerializedName("id")
    override val extId: Long,
    @SerializedName("day_id")
    override val dayId: Long,
    @SerializedName("current_date")
    override val currentDate: String,
    @SerializedName("lecture_hall")
    override val lectureHallName: String,
    @SerializedName("lesson_type")
    override val lectureTypeName: String,
    @SerializedName("lesson_start")
    override val lessonStart: String,
    @SerializedName("lesson_end")
    override val lessonEnd: String,
    @SerializedName("subgroups")
    override val subgroupList: List<Subgroup>,
    @SerializedName("subject")
    override val subjectName: String,
    @SerializedName("teacher_name")
    override val teacherName: String,
    @SerializedName("teacher_id")
    override val teacherExtId: Long,
    @SerializedName("lesson_status")
    override val lessonStatus: Int
) : AbstractLesson<Subgroup>