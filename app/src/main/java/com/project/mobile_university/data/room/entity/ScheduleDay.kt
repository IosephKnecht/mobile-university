package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScheduleDay(@PrimaryKey @ColumnInfo(name = "current_date") val currentDate: String)