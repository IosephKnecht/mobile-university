package com.project.mobile_university.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.project.mobile_university.data.room.entity.Subgroup

@Dao
interface SubgroupDao {
    @Insert
    fun insert(vararg subgroups: Subgroup)

    @Update
    fun update(vararg subgroups: Subgroup)

    @Delete
    fun delete(vararg subgroups: Subgroup)
}