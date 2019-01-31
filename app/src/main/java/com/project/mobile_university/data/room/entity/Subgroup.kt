package com.project.mobile_university.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subgroup(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                    val humanValue: Long,
                    val name: String)