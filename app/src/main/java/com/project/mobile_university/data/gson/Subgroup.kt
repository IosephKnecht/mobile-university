package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class Subgroup(@SerializedName("human_value") val humanValue: Long,
                    @SerializedName("name") val name: String)