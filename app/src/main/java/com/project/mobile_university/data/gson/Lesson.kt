package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class Lesson(@SerializedName("lecture_hall") val lectureHallName: String,
                  @SerializedName("lesson_type") val lectureTypeName: String,
                  @SerializedName("schedule_enum") val scheduleEnum: String,
                  @SerializedName("subgroups") val subgroupList: List<String>,
                  @SerializedName("subject") val subjectName: String,
                  @SerializedName("teacher") val teacherName: String)