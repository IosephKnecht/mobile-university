package com.project.mobile_university.presentation.schedule

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.project.mobile_university.data.gson.Lesson

@BindingAdapter("schedule_enum")
fun TextView.scheduleEnum(lesson: Lesson) {
    val scheduleEnumString = "${lesson.lessonStart} - \n${lesson.lessonEnd}"
    text = scheduleEnumString
}