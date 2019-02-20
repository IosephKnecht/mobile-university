package com.project.mobile_university.data.room.tuple

import androidx.room.Relation
import com.project.mobile_university.data.room.entity.Lesson

data class ScheduleDayWithLessons(var id: Long = -1,
                                  var currentDate: String = "",
                                  var extId: Long = -1,
                                  @Relation(entity = Lesson::class,
                                      parentColumn = "id",
                                      entityColumn = "day_id")
                                  var lessons: List<Lesson> = listOf())