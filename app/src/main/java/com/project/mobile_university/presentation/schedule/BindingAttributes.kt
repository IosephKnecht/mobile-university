package com.project.mobile_university.presentation.schedule

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.LessonType

@BindingAdapter("schedule_enum")
fun TextView.scheduleEnum(lesson: Lesson?) {
    if (lesson == null) return

    val scheduleEnumString = "${lesson.lessonStart} - \n${lesson.lessonEnd}"
    text = scheduleEnumString
}

@BindingAdapter("lesson_status_background")
fun View.setLessonStatusBackground(lessonStatus: LessonStatus?) {
    val backgroundColor = lessonStatus?.itemColor ?: return

    setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
}

@BindingAdapter("lesson_type")
fun TextView.setLessonType(lessonType: LessonType?) {
    lessonType?.let {
        text = context.getString(it.description)
    }
}