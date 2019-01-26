package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = ScheduleDay::class, parentColumns = ["current_date"], childColumns = ["current_date"])])
data class Lesson(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                  @ColumnInfo(name = "current_date") val currentDate: String,
                  val lectureHallName: String,
                  val lectureTypeName: String,
                  val lessonStart: String,
                  val lessonEnd: String,
                  val subjectName: String,
                  val teacherName: String)