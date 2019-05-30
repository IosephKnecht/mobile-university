package com.project.mobile_university.presentation.teachers.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.presentation.common.ui.ViewHolder
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class TeachersAdapter(private val clickListener: (userId: Long) -> Unit) : ViewModelAdapter() {
    init {
        cell<Teacher>(R.layout.item_teacher, BR.teacher)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val teacher = getViewModel(position) as Teacher

        holder.itemView.setOnClickListener { clickListener.invoke(teacher.id) }
    }
}