package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class ScheduleDay(@SerializedName("current_date") val currentDate: String,
                       @SerializedName("lessons") val lessons: List<Lesson>)