package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.shared.AbstractDao

@Dao
interface LessonDao : AbstractDao<Lesson> {
    @Query("""Select * from lesson where lesson.day_id = :dayId""")
    fun getLessonByScheduleDayId(dayId: Long): List<Lesson>

    @Query("""Select * from lesson where lesson.ext_id = :lessonExtId""")
    fun getLessonByExtId(lessonExtId: Long): Lesson
}