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
        parentColumn = "id",
        entityColumn = "day_id"
    ) var lessons: List<Lesson> = listOf()
)