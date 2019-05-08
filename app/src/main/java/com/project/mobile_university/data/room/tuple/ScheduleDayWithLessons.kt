package com.project.mobile_university.data.room.tuple

import androidx.room.Embedded
import androidx.room.Relation
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.ScheduleDay

data class ScheduleDayWithLessons(
    @Embedded
    var scheduleDay: ScheduleDay? = null,
    @Relation(
        entity = Lesson::class,
        parentColumn = "ext_id",
        entityColumn = "day_ext_id"
    ) var lessons: List<Lesson> = listOf()
)