package com.project.mobile_university.presentation.teachers.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class TeachersAdapter : ViewModelAdapter() {
    init {
        cell<Teacher>(R.layout.item_teacher, BR.teacher)
    }
}