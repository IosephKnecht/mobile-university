package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["ext_id"], unique = true), Index(value = ["name"], unique = true)])
data class Subgroup(
    @PrimaryKey
    @ColumnInfo(name="ext_id")
    val id: Long,
    @ColumnInfo(name = "human_value")
    val humanValue: Long,
    @ColumnInfo(name = "name")
    val name: String
)