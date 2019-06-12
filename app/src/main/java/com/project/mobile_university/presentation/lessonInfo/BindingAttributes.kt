package com.project.mobile_university.presentation.lessonInfo

import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.LessonType

@BindingAdapter("setLessonType")
fun TextView.convertLessonType(lessonType: LessonType?) {
    lessonType?.let {
        text = context.format(R.string.lesson_type_string, context.getString(it.description))
    }
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

@BindingAdapter("setLessonStatus")
fun TextView.setLessonStatus(lessonStatus: LessonStatus?) {
    if (lessonStatus == null) return

    text = context.format(
        R.string.lesson_status_string,
        context.getString(lessonStatus.description)
    )
}

@BindingAdapter("check_list_text")
fun Button.setCheckListStatus(checkListId: Long?) {
    text = if (checkListId == null) {
        context.getString(R.string.lesson_info_check_list_create)
    } else {
        context.getString(R.string.lesson_info_check_list_show)
    }
}

private fun Context.format(@StringRes stringValue: Int, argument: String?): String {
    return getString(stringValue).format(argument)
}