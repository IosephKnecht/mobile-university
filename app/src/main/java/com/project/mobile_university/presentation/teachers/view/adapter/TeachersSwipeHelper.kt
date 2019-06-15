package com.project.mobile_university.presentation.teachers.view.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.presentation.common.helpers.swipe.SwipeHelper
import com.project.mobile_university.presentation.common.helpers.swipe.SwipeModel
import com.project.mobile_university.presentation.common.helpers.swipe.UnderlayButton

class TeachersSwipeHelper(
    context: Context,
    swipeDirection: Int,
    recyclerView: RecyclerView,
    itemWidth: Int,
    private val click: (position: Int, swipeAction: SwipeAction) -> Unit
) : SwipeHelper(
    context,
    swipeDirection,
    recyclerView,
    itemWidth
) {
    override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder): List<UnderlayButton> {
        val context = viewHolder.itemView.context
        val dividerColor = ContextCompat.getColor(context, R.color.divider)
        val size = context.resources.getDimensionPixelOffset(R.dimen.swipe_drawable_width)

        return listOf(
            SwipeModel.Drawable(
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_schedule)!!,
                tintColor = ContextCompat.getColor(context, R.color.accent),
                size = size,
                clickListener = { position -> click.invoke(position, SwipeAction.SCHEDULE_RANGE) },
                dividerColor = dividerColor
            ),
            SwipeModel.Drawable(
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_profile)!!,
                tintColor = ContextCompat.getColor(context, R.color.primary_dark),
                size = size,
                clickListener = { position -> click.invoke(position, SwipeAction.PROFILE) },
                dividerColor = dividerColor
            )
        )
    }
}