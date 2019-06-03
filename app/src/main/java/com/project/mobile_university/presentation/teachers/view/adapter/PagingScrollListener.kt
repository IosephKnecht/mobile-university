package com.project.mobile_university.presentation.teachers.view.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMoreItems: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lastVisibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount - 1

        if (lastVisibleItemCount == totalItemCount) {
            loadMoreItems.invoke()
        }
    }
}