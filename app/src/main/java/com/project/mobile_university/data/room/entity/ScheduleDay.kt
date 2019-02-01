package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id", "current_date"], unique = true)])
data class ScheduleDay(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                       @ColumnInfo(name = "current_date") val currentDate: String)