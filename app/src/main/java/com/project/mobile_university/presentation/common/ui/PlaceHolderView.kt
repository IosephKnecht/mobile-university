package com.project.mobile_university.presentation.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.mobile_university.R
import kotlinx.android.synthetic.main.empty_holder.view.*

class PlaceHolderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    sealed class State(@DrawableRes open val iconRes: Int) {

        class Empty(
            @StringRes val contentRes: Int,
            override val iconRes: Int
        ) : State(iconRes)

        class Error(
            val content: String,
            override val iconRes: Int
        ) : State(iconRes)
    }

    private val textView: TextView
    private val imageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.empty_holder, this, true)
        textView = content
        imageView = icon
    }

    fun setState(state: State) {
        when (state) {
            is State.Empty -> {
                textView.setText(state.contentRes)
            }
            is State.Error -> {
                textView.text = state.content
            }
        }

        imageView.setImageResource(state.iconRes)
    }
}