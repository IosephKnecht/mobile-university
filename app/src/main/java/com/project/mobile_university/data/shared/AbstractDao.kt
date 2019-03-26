package com.project.mobile_university.data.shared

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface AbstractDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg elements: T): List<Long>

    @Update
    fun update(vararg elements: T)

    @Delete
    fun delete(vararg elements: T)
}