package com.project.mobile_university.data.presentation

data class Lesson(val id: Long,
                  val dayId: Long,
                  val currentDate: String,
                  val lectureHallName: String,
                  val lectureTypeName: String,
                  val lessonStart: String,
                  val lessonEnd: String,
                  val subgroupList: List<Subgroup>,
                  val subjectName: String,
                  val teacherName: String)