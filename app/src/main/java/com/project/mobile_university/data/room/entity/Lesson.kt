package com.project.mobile_university.data.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.project.mobile_university.data.shared.AbstractEntity
import com.project.mobile_university.data.shared.AbstractLesson

@Entity(foreignKeys = [ForeignKey(entity = ScheduleDay::class, parentColumns = ["id"], childColumns = ["day_id"], onDelete = CASCADE)],
    indices = [Index(value = ["day_id"])])
data class Lesson(@PrimaryKey(autoGenerate = true)
                  override var id: Long = 0,
                  @ColumnInfo(name = "day_id")
                  override var dayId: Long = 0,
                  @ColumnInfo(name = "ext_id")
                  override var extId: Long = 0,
                  @ColumnInfo(name = "current_date")
                  override var currentDate: String = "",
                  override var lectureHallName: String = "",
                  override var lectureTypeName: String = "",
                  override var lessonStart: String = "",
                  override var lessonEnd: String = "",
                  override var subjectName: String = "",
                  override var teacherName: String = "",
                  @ColumnInfo(name = "teacher_ext_id")
                  override var teacherExtId: Long = -1,
                  @Ignore
                  override var subgroupList: List<Subgroup> = listOf()) : AbstractEntity, AbstractLesson<Subgroup>