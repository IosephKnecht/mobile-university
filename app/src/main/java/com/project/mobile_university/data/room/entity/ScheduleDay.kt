package com.project.mobile_university.data.room.entity

import androidx.room.*
import com.project.mobile_university.data.shared.AbstractEntity
import com.project.mobile_university.data.shared.AbstractScheduleDay

@Entity(indices = [Index(value = ["ext_id"], unique = true)])
data class ScheduleDay(@PrimaryKey(autoGenerate = true)
                       override var id: Long = 0,
                       @ColumnInfo(name = "ext_id")
                       override var extId: Long = -1,
                       @ColumnInfo(name = "current_date")
                       override var currentDate: String = "",
                       @Ignore
                       override var lessons: List<Lesson> = listOf()) : AbstractEntity, AbstractScheduleDay<Lesson>