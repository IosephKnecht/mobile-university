package com.project.mobile_university.presentation.lessonInfo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.Subgroup
import com.project.mobile_university.databinding.ItemSubgroupBinding

class SubgroupAdapter : RecyclerView.Adapter<SubgroupAdapter.ViewHolder>() {

    var subgroupList = listOf<Subgroup>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemSubgroupBinding>(LayoutInflater.from(parent.context),
            R.layout.item_subgroup, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = subgroupList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = subgroupList[position]
    }

    class ViewHolder(val binding: ItemSubgroupBinding) : RecyclerView.ViewHolder(binding.root)
}