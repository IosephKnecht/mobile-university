package com.project.mobile_university.data.room.entity

import androidx.room.*

@Entity(indices = [Index(value = ["current_date"], unique = true)])
data class ScheduleDay(
    @PrimaryKey
    @ColumnInfo(name = "ext_id")
    var id: Long = -1,
    @ColumnInfo(name = "current_date")
    var currentDate: String = "",
    @Ignore
    var lessons: List<Lesson> = listOf()
)