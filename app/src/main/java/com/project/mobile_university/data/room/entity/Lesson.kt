package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = ScheduleDay::class, parentColumns = ["id"], childColumns = ["day_id"], onDelete = CASCADE)])
data class Lesson(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                  @ColumnInfo(name = "day_id") var dayId: Long,
                  @ColumnInfo(name = "ext_id") val extId: Long,
                  @ColumnInfo(name = "current_date") val currentDate: String,
                  val lectureHallName: String,
                  val lectureTypeName: String,
                  val lessonStart: String,
                  val lessonEnd: String,
                  val subjectName: String,
                  val teacherName: String)