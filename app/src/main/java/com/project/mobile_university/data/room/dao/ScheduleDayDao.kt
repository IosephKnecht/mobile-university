package com.project.mobile_university.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.mobile_university.data.room.entity.ScheduleDay

@Dao
interface ScheduleDayDao {
    @Insert
    fun insert(vararg days: ScheduleDay): List<Long>

    @Update
    fun update(vararg days: ScheduleDay)

    @Query("""Select * from scheduleday
        where scheduleday.`current_date` = :currentDate""")
    fun getScheduleDay(currentDate: String): ScheduleDay?

    @Query("""Select * from scheduleday
                    where scheduleday.`current_date` in (:dayIds)""")
    fun getScheduleDayList(dayIds: List<String>): List<ScheduleDay>

    @Query("""Delete from scheduleday
        where scheduleday.`current_date` in (:dayIds)
    """)
    fun deleteScheduleDayList(dayIds: List<String>)

    @Query("""Delete from scheduleday
        where scheduleday.`current_date` = :date
    """)
    fun deleteScheduleDayByDate(date: String)
}