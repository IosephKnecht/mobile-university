package com.project.mobile_university.data.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = [ForeignKey(
        entity = ScheduleDay::class,
        parentColumns = ["ext_id"],
        childColumns = ["day_ext_id"],
        onDelete = CASCADE
    )],
    indices = [Index(value = ["ext_id"], unique = true), Index(value = ["day_ext_id"])]
)
data class Lesson(
    @PrimaryKey
    @ColumnInfo(name = "ext_id")
    var id: Long = 0,
    @ColumnInfo(name = "day_ext_id")
    var dayExtId: Long = 0,
    @ColumnInfo(name = "current_date")
    var currentDate: String = "",
    @ColumnInfo(name = "lecture_hall_name")
    var lectureHallName: String = "",
    @ColumnInfo(name = "lecture_type_name")
    var lectureTypeName: String = "",
    @ColumnInfo(name = "lesson_start")
    var lessonStart: String = "",
    @ColumnInfo(name = "lesson_end")
    var lessonEnd: String = "",
    @ColumnInfo(name = "subject_name")
    var subjectName: String = "",
    @ColumnInfo(name = "teacher_name")
    var teacherName: String = "",
    @ColumnInfo(name = "teacher_ext_id")
    var teacherExtId: Long = -1,
    @Ignore
    var subgroupList: List<Subgroup> = listOf(),
    @ColumnInfo(name = "lesson_status")
    var lessonStatus: Int = -1
)