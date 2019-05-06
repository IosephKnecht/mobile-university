package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.Subgroup
import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
import com.project.mobile_university.data.shared.AbstractDao

@Dao
interface LessonSubgroupDao : AbstractDao<LessonSubgroup> {
    @Transaction
    @Query(
        """Select lesson.id, lesson.day_id, lesson.ext_id, lesson.`current_date`,
         lesson.lectureHallName, lesson.lectureTypeName, lesson.lessonStart, lesson.lessonEnd,
          lesson.subjectName, lesson.teacherName, lesson.teacher_ext_id, lesson.lessonStatus, lesson.day_ext_id from lessonsubgroup
        inner join subgroup on subgroup.ext_id = lessonsubgroup.subgroup_id
        inner join lesson on lesson.ext_id = lessonsubgroup.lesson_id
        where lessonsubgroup.lesson_id = :lessonExtId"""
    )
    fun getLessonWithSubgroups(lessonExtId: Long): LessonWithSubgroups

    @Query("""Delete from lessonsubgroup where lessonsubgroup.lesson_id = :lessonExtId""")
    fun removeRelationsForLesson(lessonExtId: Long)
}