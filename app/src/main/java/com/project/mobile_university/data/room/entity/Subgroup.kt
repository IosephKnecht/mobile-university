package com.project.mobile_university.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.mobile_university.data.shared.AbstractEntity
import com.project.mobile_university.data.shared.AbstractSubgroup

@Entity(indices = [Index(value = ["ext_id"], unique = true)])
data class Subgroup(@PrimaryKey(autoGenerate = true)
                    override var id: Long = 0,
                    @ColumnInfo(name = "ext_id")
                    override val extId: Long,
                    override val humanValue: Long,
                    override val name: String) : AbstractEntity, AbstractSubgroup