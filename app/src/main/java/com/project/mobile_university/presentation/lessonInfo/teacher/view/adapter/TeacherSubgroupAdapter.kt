package com.project.mobile_university.presentation.lessonInfo.teacher.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.Subgroup
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class TeacherSubgroupAdapter : ViewModelAdapter() {
    init {
        cell<Subgroup>(R.layout.item_subgroup_teacher, BR.model)
    }
}