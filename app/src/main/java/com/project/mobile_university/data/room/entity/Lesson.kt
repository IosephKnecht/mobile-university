package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lesson(@PrimaryKey(autoGenerate = true) val id: Long,
                  @ColumnInfo(name = "current_date") val currentDate: String,
                  val lectureHallName: String,
                  val lectureTypeName: String,
                  val lessonStart: String,
                  val lessonEnd: String,
                  val subjectName: String,
                  val teacherName: String)