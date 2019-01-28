package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.Lesson

@Dao
interface LessonDao {
    @Insert
    fun insert(vararg lessons: Lesson): List<Long>

    @Update
    fun update(vararg lessons: Lesson)

    @Delete
    fun delete(vararg lessons: Lesson)

    @Query("""Select * from lesson where lesson.day_id = :dayId""")
    fun getLessonByScheduleDayId(dayId: Long): List<Lesson>
}