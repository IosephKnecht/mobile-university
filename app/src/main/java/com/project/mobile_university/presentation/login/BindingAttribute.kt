package com.project.mobile_university.presentation.login

import android.databinding.BindingConversion
import android.view.View

@BindingConversion
fun visibleOrGone(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}