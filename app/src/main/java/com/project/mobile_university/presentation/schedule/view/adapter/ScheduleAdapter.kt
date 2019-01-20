package com.project.mobile_university.presentation.schedule.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.data.gson.Lesson
import com.project.mobile_university.databinding.ItemLessonBinding

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    var lessonList: List<Lesson> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLessonBinding>(LayoutInflater.from(parent.context),
            R.layout.item_lesson, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = lessonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.lesson = lessonList[position]
    }

    class ViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root)
}