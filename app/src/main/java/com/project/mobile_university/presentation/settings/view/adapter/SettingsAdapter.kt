package com.project.mobile_university.presentation.settings.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.SettingsItem
import com.project.mobile_university.databinding.ItemSettingsBinding

class SettingsAdapter(val settingItems: List<SettingsItem>)
    : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    var exitListener: (() -> Unit)? = null
    var clearListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemSettingsBinding>(
            LayoutInflater.from(parent.context), R.layout.item_settings,
            parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = settingItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val settingsItem = settingItems[position]

        holder.binding.item = settingsItem

        when (settingsItem.id) {
            SettingsEnum.EXIT.id -> {
                holder.binding.root.setOnClickListener { exitListener?.invoke() }
            }
            SettingsEnum.CLEAR_CACHE.id -> {
                holder.binding.root.setOnClickListener { clearListener?.invoke() }
            }
        }
    }

    class ViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)
}