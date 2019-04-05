package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.Subgroup
import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
import com.project.mobile_university.data.shared.AbstractDao

@Dao
interface LessonSubgroupDao : AbstractDao<LessonSubgroup> {

    @Query("""Select subgroup.id, subgroup.humanValue, subgroup.name, subgroup.ext_id from subgroup
        inner join lessonsubgroup on subgroup.id = lessonsubgroup.subgroup_id
        where lessonsubgroup.lesson_id = :lessonId""")
    fun getSubgroupIdsByLessonId(lessonId: Long): List<Subgroup>

    @Transaction
    @Query("""Select lesson.id, lesson.day_id, lesson.ext_id, lesson.`current_date`,
         lesson.lectureHallName, lesson.lectureTypeName, lesson.lessonStart, lesson.lessonEnd,
          lesson.subjectName, lesson.teacherName, lesson.teacher_ext_id from lessonsubgroup
        inner join subgroup on subgroup.id = lessonsubgroup.subgroup_id
        inner join lesson on lesson.id = lessonsubgroup.lesson_id
        where lessonsubgroup.lesson_id = :lessonId""")
    fun getLessonWithSubgroups(lessonId: Long): LessonWithSubgroups
}