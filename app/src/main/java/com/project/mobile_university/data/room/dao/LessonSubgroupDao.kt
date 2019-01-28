package com.project.mobile_university.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.Subgroup

@Dao
interface LessonSubgroupDao {
    @Insert
    fun insert(vararg lessonSubgroup: LessonSubgroup)

    @Delete
    fun delete(vararg lessonSubgroup: LessonSubgroup)

//    @Query("""Select id, day_id, ext_id, current_date, lectureHallName, lectureTypeName,
//         lessonStart, lessonEnd, subjectName, teacherName from lesson
//         inner join lessonsubgroup on
//        lesson.id = lessonsubgroup.lesson_id
//        where lessonsubgroup.subgroup_id = :subgroupId"""
//    )
//    fun getLessonForSubgroup(subgroupId: Long): List<Lesson>

    @Query("""Select subgroup.id, subgroup.humanValue, subgroup.name from subgroup
        inner join lessonsubgroup on subgroup.id = lessonsubgroup.subgroup_id
        where lessonsubgroup.lesson_id = :lessonId""")
    fun getSubgroupIdsByLessonId(lessonId: Long): List<Subgroup>

//    @Query("""Select subgroup.id, subgroup.humanValue, subgroup.name from subgroup
//        inner join lessonsubgroup on lessonsubgroup.subgroup_id = subgroup.id
//        where lessonsubgroup.lesson_id in (:lessonIds)
//    """)
//    fun getSubgroupListInLessonIds(lessonIds: List<Long>): List<Subgroup>

//    @Query("""Delete from lessonsubgroup where lessonsubgroup.lesson_id = :lessonId""")
//    fun deleteSubgroupByLesson(lessonId: Long)
}