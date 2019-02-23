package com.project.mobile_university.data.room.tuple

import androidx.room.Relation
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.shared.AbstractEntity
import com.project.mobile_university.data.shared.AbstractScheduleDay

data class ScheduleDayWithLessons(override var id: Long = -1,
                                  override var currentDate: String = "",
                                  override var extId: Long = -1,
                                  @Relation(entity = Lesson::class,
                                      parentColumn = "id",
                                      entityColumn = "day_id")
                                  override var lessons: List<Lesson> = listOf()) : AbstractEntity, AbstractScheduleDay<Lesson>