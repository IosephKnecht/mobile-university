package com.project.mobile_university.presentation.schedule.subgroup.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.databinding.ItemLessonBinding

class ScheduleSubgroupAdapter : RecyclerView.Adapter<ScheduleSubgroupAdapter.ViewHolder>() {
    var scheduleDayList: List<ScheduleDay> = listOf()
    var currentDate: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLessonBinding>(LayoutInflater.from(parent.context),
            R.layout.item_lesson, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        val currentDay = scheduleDayList.find { it.currentDate == currentDate }
        return if (currentDay == null) 0 else currentDay.lessons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDay = scheduleDayList.find { it.currentDate == currentDate }
        if (currentDay != null) {
            holder.binding.lesson = currentDay.lessons[position]
        }
    }

    class ViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root)
}