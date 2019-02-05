package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import io.reactivex.Observable
import java.util.*

@Dao
interface ScheduleDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg days: ScheduleDay): List<Long>

    @Update
    fun update(vararg days: ScheduleDay)

    @Query("""Select * from scheduleday
                    where scheduleday.`current_date` in (:dayIds)""")
    fun getScheduleDayList(dayIds: List<String>): List<ScheduleDay>

    @Transaction
    @Query("""Select scheduleday.id,
        scheduleday.`current_date` as currentDate from scheduleday
        inner join lesson on lesson.day_id = scheduleday.id
        inner join lessonsubgroup on lessonsubgroup.subgroup_id = subgroup_id
        inner join subgroup on subgroup.id = lessonsubgroup.subgroup_id
        where scheduleday.`current_date` in (:dayIds) and subgroup.ext_id = :subgroupId""")
    fun getScheduleDayWithLessonsForSubgroup(dayIds: List<String>, subgroupId: Long): List<ScheduleDayWithLessons>

    @Query("""Delete from scheduleday
        where scheduleday.`current_date` in (:dayIds)
    """)
    fun deleteScheduleDayList(dayIds: List<String>)

    @Delete
    fun delete(vararg scheduleDay: ScheduleDay)
}