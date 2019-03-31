package com.project.mobile_university.data.room.tuple

import androidx.room.Embedded
import androidx.room.Relation
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.Subgroup

data class LessonWithSubgroups(
    @Embedded
    var lesson: Lesson? = null,
    @Relation(entity = Subgroup::class,
        entityColumn = "id",
        parentColumn = "id")
    var subgroupList: List<Subgroup> = listOf()
)