package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName
import com.project.mobile_university.data.shared.AbstractScheduleDay

data class ScheduleDay(@SerializedName("id")
                       override val extId: Long,
                       @SerializedName("current_date")
                       override val currentDate: String,
                       @SerializedName("lessons")
                       override val lessons: List<Lesson>) : AbstractScheduleDay<Lesson>