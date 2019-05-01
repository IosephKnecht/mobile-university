package com.project.mobile_university.presentation.schedule.subgroup.view.adapter

import androidx.databinding.library.baseAdapters.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.presentation.common.ui.ViewHolder
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter


class ScheduleSubgroupAdapter(private val lessonClick: (lessonId: Long) -> Unit) : ViewModelAdapter() {
    init {
        cell<Lesson>(R.layout.item_lesson, BR.lesson)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val lesson = getViewModel(position) as Lesson
        holder.itemView.setOnClickListener { lessonClick.invoke(lesson.extId) }
    }
}