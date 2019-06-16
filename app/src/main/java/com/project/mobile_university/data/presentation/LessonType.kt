package com.project.mobile_university.data.presentation

import androidx.annotation.StringRes
import com.project.mobile_university.R

enum class LessonType(
    val value: Int,
    @StringRes
    val description: Int
) {
    LECTURE_LESSON(value = 0, description = R.string.lesson_type_lecture_description),
    PRACTICAL_LESSON(value = 1, description = R.string.lesson_type_practice_description),
    SEMINAR(value = 2, description = R.string.lesson_seminar_description),
    COLLOQUIUM(value = 3, description = R.string.lesson_type_colloquium);
}