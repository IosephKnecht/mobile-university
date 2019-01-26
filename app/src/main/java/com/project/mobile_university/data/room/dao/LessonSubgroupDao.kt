package com.project.mobile_university.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.LessonSubgroup

@Dao
interface LessonSubgroupDao {
    @Insert
    fun insert(vararg lessonSubgroup: LessonSubgroup)

    @Delete
    fun delete(vararg lessonSubgroup: LessonSubgroup)

    @Query(
        """Select id, current_date, lectureHallName, lectureTypeName,
         lessonStart, lessonEnd, subjectName, teacherName from lesson
         inner join lessonsubgroup on
        lesson.id = lessonsubgroup.lesson_id
        where lessonsubgroup.subgroup_id = :subgroupId"""
    )
    fun getLessonForSubgroup(subgroupId: Long): List<Lesson>
}