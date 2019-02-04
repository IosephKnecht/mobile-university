package com.project.mobile_university.data.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(entity = ScheduleDay::class, parentColumns = ["id"], childColumns = ["day_id"], onDelete = CASCADE)],
    indices = [Index(value = ["day_id"])])
data class Lesson(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                  @ColumnInfo(name = "day_id") var dayId: Long = 0,
                  @ColumnInfo(name = "ext_id") var extId: Long = 0,
                  @ColumnInfo(name = "current_date") var currentDate: String = "",
                  var lectureHallName: String = "",
                  var lectureTypeName: String = "",
                  var lessonStart: String = "",
                  var lessonEnd: String = "",
                  var subjectName: String = "",
                  var teacherName: String = "",
                  @Ignore
                  var subgroupList: List<Subgroup> = listOf())