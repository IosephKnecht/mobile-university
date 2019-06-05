package com.project.mobile_university.presentation

import android.view.View
import androidx.databinding.BindingAdapter

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(condition: Boolean) {
    visible(condition)
}