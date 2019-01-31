package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.Subgroup

@Dao
interface SubgroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subgroups: Subgroup): List<Long>

    @Update
    fun update(vararg subgroups: Subgroup)

    @Delete
    fun delete(vararg subgroups: Subgroup)
}