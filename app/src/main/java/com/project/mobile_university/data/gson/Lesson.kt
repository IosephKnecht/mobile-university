package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class Lesson(@SerializedName("id")
                  val id: Long,
                  @SerializedName("day_id")
                  val dayId: Long,
                  @SerializedName("current_date")
                  val currentDate: String,
                  @SerializedName("lecture_hall")
                  val lectureHallName: String,
                  @SerializedName("lesson_type")
                  val lectureTypeName: String,
                  @SerializedName("lesson_start")
                  val lessonStart: String,
                  @SerializedName("lesson_end")
                  val lessonEnd: String,
                  @SerializedName("subgroups")
                  val subgroupList: List<Subgroup>,
                  @SerializedName("subject")
                  val subjectName: String,
                  @SerializedName("teacher_name")
                  val teacherName: String,
                  @SerializedName("teacher_id")
                  val teacherId: Long)