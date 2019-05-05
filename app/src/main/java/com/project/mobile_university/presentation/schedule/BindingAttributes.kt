package com.project.mobile_university.presentation.schedule

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus

@BindingAdapter("schedule_enum")
fun TextView.scheduleEnum(lesson: Lesson?) {
    if (lesson == null) return

    val scheduleEnumString = "${lesson.lessonStart} - \n${lesson.lessonEnd}"
    text = scheduleEnumString
}

@BindingAdapter("lesson_status_background")
fun View.setLessonStatusBackground(lessonStatus: Int?) {
    val fromIntLessonStatus = LessonStatus.fromInt(lessonStatus) ?: return

    setBackgroundColor(ContextCompat.getColor(context, fromIntLessonStatus.itemColor))
}