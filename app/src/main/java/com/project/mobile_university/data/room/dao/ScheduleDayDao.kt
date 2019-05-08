package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.room.entity.Subgroup
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons

@Dao
abstract class ScheduleDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertDays(vararg scheduleDays: ScheduleDay): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertLessons(vararg lessons: Lesson): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertSubgroups(vararg subgroups: Subgroup): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertLessonSubgroup(vararg lessonSubgroup: LessonSubgroup): List<Long>

    @Query(
        """Select subgroup.ext_id,subgroup.name, subgroup.human_value from lessonsubgroup
        inner join subgroup on subgroup.ext_id = lessonsubgroup.subgroup_id
        where lessonsubgroup.lesson_id = :lessonExtId"""
    )
    protected abstract fun getSubgroupsForLesson(lessonExtId: Long): List<Subgroup>

    @Query("""Select * from lesson where lesson.ext_id = :lessonExtId""")
    protected abstract fun getLessonByExtId(lessonExtId: Long): Lesson

    @Transaction
    open fun insertScheduleDay(
        scheduleDays: Array<ScheduleDay>,
        lessons: Array<Lesson>,
        subgroups: Array<Subgroup>,
        lessonSubgroup: Array<LessonSubgroup>
    ) {
        insertDays(*scheduleDays)
        insertLessons(*lessons)
        insertSubgroups(*subgroups)
        insertLessonSubgroup(*lessonSubgroup)
    }

    @Transaction
    open fun insertLessons(
        lessons: Array<Lesson>,
        subgroups: Array<Subgroup>,
        lessonSubgroup: Array<LessonSubgroup>
    ) {
        insertLessons(*lessons)
        insertSubgroups(*subgroups)
        insertLessonSubgroup(*lessonSubgroup)
    }

    @Transaction
    @Query(
        """Select scheduleday.ext_id, scheduleday.`current_date` from scheduleday
		inner join lesson on lesson.day_ext_id = scheduleday.ext_id
		inner join lessonsubgroup on lessonsubgroup.lesson_id  = lesson.ext_id
        where scheduleday.`current_date` in (:dayIds) and lessonsubgroup.subgroup_id = :subgroupId"""
    )
    abstract fun getScheduleDayWithLessonsForSubgroup(
        dayIds: List<String>,
        subgroupId: Long
    ): List<ScheduleDayWithLessons>

    @Transaction
    @Query(
        """Select scheduleday.ext_id, scheduleday.`current_date` from scheduleday
        inner join lesson on lesson.day_ext_id = scheduleday.ext_id
        where scheduleday.`current_date` in (:dayIds) and lesson.teacher_ext_id = :teacherExtId"""
    )
    abstract fun getScheduleDayWithLessonsForTeacher(
        dayIds: List<String>,
        teacherExtId: Long
    ): List<ScheduleDayWithLessons>


    @Transaction
    open fun getLessonWithSubgroups(lessonExtId: Long): Lesson {
        val lesson = getLessonByExtId(lessonExtId)
        val subgroups = getSubgroupsForLesson(lessonExtId)
        lesson.subgroupList = subgroups

        return lesson
    }
}