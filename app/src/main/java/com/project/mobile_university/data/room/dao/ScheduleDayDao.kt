package com.project.mobile_university.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.mobile_university.data.room.entity.ScheduleDay

@Dao
interface ScheduleDayDao {
    @Insert
    fun insert(vararg days: ScheduleDay)

    @Update
    fun update(vararg days: ScheduleDay)

    @Query("""Select * from scheduleday
        where scheduleday.`current_date` = :currentDate""")
    fun getScheduleDay(currentDate: String): List<ScheduleDay>
}