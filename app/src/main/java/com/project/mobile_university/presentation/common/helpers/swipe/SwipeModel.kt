package com.project.mobile_university.presentation.common.helpers.swipe

import android.graphics.*
import androidx.core.graphics.drawable.DrawableCompat

sealed class SwipeModel : UnderlayButton {
    class Drawable(
        private val drawable: android.graphics.drawable.Drawable,
        private val tintColor: Int,
        private val size: Int,
        private val dividerColor: Int,
        private val clickListener: ((position: Int) -> Unit)? = null
    ) : SwipeModel() {

        var position: Int? = null
            private set

        var clickRegion: RectF? = null
            private set

        override fun onClick(x: Float, y: Float): Boolean {
            return clickRegion?.takeIf { it.contains(x, y) }
                ?.run {
                    position?.let { nonNullPosition -> clickListener?.invoke(nonNullPosition) }
                    true
                } ?: false
        }

        override fun onDraw(canvas: Canvas, rect: RectF, position: Int, index: Int) {
            val offsetX = rect.width() * index
            val offsetY = (rect.height() / 2).toInt() - (size / 2)
            val offsetCenterX = ((rect.width() / 2) - (size / 2)).toInt()

            val left = (rect.left - offsetX).toInt() + offsetCenterX
            val top = rect.top.toInt() + offsetY
            val right = (rect.right - offsetX).toInt() - offsetCenterX
            val bottom = rect.bottom.toInt() - offsetY

            DrawableCompat.setTint(drawable, tintColor)
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)

            canvas.drawLine(
                rect.left - offsetX,
                rect.top,
                rect.left - offsetX + 1f,
                rect.bottom, Paint().apply {
                    color = dividerColor
                }
            )

            clickRegion = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
            this.position = position
        }
    }
}