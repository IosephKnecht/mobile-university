package com.project.mobile_university.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.project.mobile_university.data.room.entity.Lesson

@Dao
interface LessonDao {
    @Insert
    fun insert(vararg lessons: Lesson)

    @Update
    fun update(vararg lessons: Lesson)

    @Delete
    fun delete(vararg lessons: Lesson)
}