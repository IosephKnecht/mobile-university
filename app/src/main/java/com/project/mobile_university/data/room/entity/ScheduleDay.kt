package com.project.mobile_university.data.room.entity

import androidx.room.*

@Entity(indices = [Index(value = ["ext_id"], unique = true)])
data class ScheduleDay(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                       @ColumnInfo(name = "ext_id") var extId: Long = -1,
                       @ColumnInfo(name = "current_date") var currentDate: String = "",
                       @Ignore
                       var lessons: List<Lesson> = listOf())