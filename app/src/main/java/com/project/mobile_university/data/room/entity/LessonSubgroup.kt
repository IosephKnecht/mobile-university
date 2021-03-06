package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    primaryKeys = ["lesson_id", "subgroup_id"],
    foreignKeys = [ForeignKey(
        entity = Lesson::class,
        parentColumns = ["ext_id"],
        childColumns = ["lesson_id"],
        onDelete = CASCADE
    ),
        ForeignKey(entity = Subgroup::class, parentColumns = ["ext_id"], childColumns = ["subgroup_id"])],
    indices = [Index(value = ["subgroup_id"]), Index(value = ["lesson_id", "subgroup_id"], unique = true)]
)
data class LessonSubgroup(
    @ColumnInfo(name = "lesson_id") val lessonId: Long,
    @ColumnInfo(name = "subgroup_id") var subgroupId: Long
)