package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.shared.AbstractDao
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons

@Dao
interface ScheduleDayDao : AbstractDao<ScheduleDay> {

    @Query(
        """Select * from scheduleday
                    where scheduleday.ext_id in (:dayIds)"""
    )
    fun getScheduleDayList(dayIds: List<Long>): List<ScheduleDay>

    @Transaction
    @Query(
        """Select scheduleday.id, scheduleday.ext_id, scheduleday.`current_date` from scheduleday
        inner join lesson on lesson.ext_id = scheduleday.ext_id
        inner join lessonsubgroup on lessonsubgroup.lesson_id = lesson.ext_id
        inner join subgroup on subgroup.ext_id = lessonsubgroup.subgroup_id
        where scheduleday.`current_date` in (:dayIds) and subgroup.ext_id = :subgroupId"""
    )
    fun getScheduleDayWithLessonsForSubgroup(dayIds: List<String>, subgroupId: Long): List<ScheduleDayWithLessons>

    @Transaction
    @Query(
        """Select scheduleday.id, scheduleday.ext_id, scheduleday.`current_date` from scheduleday
        inner join lesson on lesson.day_ext_id = scheduleday.ext_id
		inner join lessonsubgroup on lessonsubgroup.lesson_id = lesson.ext_id
		inner join subgroup on subgroup.ext_id = lessonsubgroup.subgroup_id
        where scheduleday.`current_date` in (:dayIds) and lesson.teacher_ext_id = :teacherExtId"""
    )
    fun getScheduleDayWithLessonsForTeacher(dayIds: List<String>, teacherExtId: Long): List<ScheduleDayWithLessons>

    @Query(
        """Delete from scheduleday
        where scheduleday.ext_id in (:dayIds)
    """
    )
    fun deleteScheduleDayList(dayIds: List<Long>)

    @Query("""Select * from scheduleday where scheduleday.ext_id = :extId""")
    fun getScheduleDayByExtId(extId: Long): ScheduleDay
}