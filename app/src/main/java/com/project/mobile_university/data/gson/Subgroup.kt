package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName
import com.project.mobile_university.data.shared.AbstractSubgroup

data class Subgroup(@SerializedName("human_value")
                    override val humanValue: Long,
                    @SerializedName("name")
                    override val name: String,
                    @SerializedName("id")
                    override val extId: Long) : AbstractSubgroup