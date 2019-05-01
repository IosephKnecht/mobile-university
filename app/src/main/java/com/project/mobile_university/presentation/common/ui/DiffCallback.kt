package com.project.mobile_university.presentation.common.ui

import androidx.recyclerview.widget.DiffUtil

class DiffCallback(
    private val oldItems: List<Any>,
    private val newItems: List<Any>,
    private val checkAreItemsTheSame: (oldItem: Any, newItem: Any) -> Boolean,
    private val checkAreContentsTheSame: (oldItem: Any, newItem: Any) -> Boolean
) : DiffUtil.Callback(
) {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkAreItemsTheSame.invoke(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkAreContentsTheSame.invoke(oldItems[oldItemPosition], newItems[newItemPosition])
    }
}