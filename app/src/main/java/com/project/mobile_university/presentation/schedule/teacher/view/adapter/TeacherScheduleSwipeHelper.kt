package com.project.mobile_university.presentation.schedule.teacher.view.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.presentation.common.helpers.swipe.SwipeHelper
import com.project.mobile_university.presentation.common.helpers.swipe.SwipeModel
import com.project.mobile_university.presentation.common.helpers.swipe.UnderlayButton
import es.dmoral.toasty.Toasty

class TeacherScheduleSwipeHelper(
    context: Context,
    recyclerView: RecyclerView,
    swipeDirection: Int,
    itemWidth: Int
) : SwipeHelper(context, swipeDirection, recyclerView, itemWidth) {

    override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder): List<UnderlayButton> {
        val context = viewHolder.itemView.context
        val dividerColor = ContextCompat.getColor(context, R.color.divider)
        val size = context.resources.getDimensionPixelOffset(R.dimen.swipe_drawable_width)

        return listOf(
            SwipeModel.Drawable(
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_ready)!!,
                tintColor = ContextCompat.getColor(context, R.color.color_green),
                size = size,
                clickListener = { Toasty.success(context, "test").show() },
                dividerColor = dividerColor
            ),
            SwipeModel.Drawable(
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_timer)!!,
                tintColor = ContextCompat.getColor(context, android.R.color.holo_blue_light),
                size = size,
                clickListener = { Toasty.success(context, "test").show() },
                dividerColor = dividerColor
            ),
            SwipeModel.Drawable(
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_delete)!!,
                tintColor = ContextCompat.getColor(context, R.color.color_red),
                size = size,
                clickListener = { Toasty.success(context, "test").show() },
                dividerColor = dividerColor
            )
        )
    }
}