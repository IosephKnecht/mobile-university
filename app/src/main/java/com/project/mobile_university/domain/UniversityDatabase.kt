package com.project.mobile_university.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.mobile_university.data.room.dao.ScheduleDayDao
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.room.entity.Subgroup

@Database(entities = [Subgroup::class, Lesson::class, LessonSubgroup::class, ScheduleDay::class], version = 2)
abstract class UniversityDatabase : RoomDatabase() {
    abstract fun scheduleDayDao(): ScheduleDayDao
}