package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.ScheduleDay

@Dao
interface ScheduleDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg days: ScheduleDay): List<Long>

    @Update
    fun update(vararg days: ScheduleDay)

    @Query("""Select * from scheduleday
                    where scheduleday.`current_date` in (:dayIds)""")
    fun getScheduleDayList(dayIds: List<String>): List<ScheduleDay>

    @Query("""Delete from scheduleday
        where scheduleday.`current_date` in (:dayIds)
    """)
    fun deleteScheduleDayList(dayIds: List<String>)
}