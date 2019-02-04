package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["ext_id"], unique = true)])
data class ScheduleDay(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                       @ColumnInfo(name = "ext_id") val extId: Long,
                       @ColumnInfo(name = "current_date") val currentDate: String)