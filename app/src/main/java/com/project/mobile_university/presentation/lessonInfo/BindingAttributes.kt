package com.project.mobile_university.presentation.lessonInfo

import android.content.Context
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.project.mobile_university.R

@BindingAdapter("setLessonType")
fun TextView.convertLessonType(lessonType: String?) {
    text = context.format(R.string.lesson_type_string, lessonType)
}

@BindingAdapter("setSubject")
fun TextView.convertSubject(subject: String?) {
    text = context.format(R.string.subject_string, subject)
}

@BindingAdapter("setHall")
fun TextView.setHall(hall: String?) {
    text = context.format(R.string.hall_string, hall)
}

@BindingAdapter("setTeacher")
fun TextView.setTeacherName(teacherName: String?) {
    text = context.format(R.string.teacher_string, teacherName)
}

@BindingAdapter("setSubgroup")
fun TextView.setSubgroup(subgroup: String?) {
    text = context.format(R.string.subgroup_string, subgroup)
}

@BindingAdapter("setHumanValue")
fun TextView.setHumanValue(humanValue: Long?) {
    text = context.format(R.string.human_value_string, humanValue?.toString())
}

private fun Context.format(@StringRes stringValue: Int, argument: String?): String {
    return getString(stringValue).format(argument)
}