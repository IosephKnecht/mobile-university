package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(primaryKeys = ["lesson_id", "subgroup_id"],
    foreignKeys = [ForeignKey(entity = Lesson::class, parentColumns = ["id"], childColumns = ["lesson_id"], onDelete = CASCADE),
        ForeignKey(entity = Subgroup::class, parentColumns = ["id"], childColumns = ["subgroup_id"], onDelete = CASCADE)],
    indices = [Index(value = ["lesson_id", "subgroup_id"])])
data class LessonSubgroup(@ColumnInfo(name = "lesson_id") val lessonId: Long,
                          @ColumnInfo(name = "subgroup_id") val subgroupId: Long)