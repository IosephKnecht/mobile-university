package com.project.mobile_university.presentation.schedule.subgroup

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.project.mobile_university.data.presentation.Lesson

@BindingAdapter("schedule_enum")
fun TextView.scheduleEnum(lesson: Lesson?) {
    if (lesson == null) return

    val scheduleEnumString = "${lesson.lessonStart} - \n${lesson.lessonEnd}"
    text = scheduleEnumString
}