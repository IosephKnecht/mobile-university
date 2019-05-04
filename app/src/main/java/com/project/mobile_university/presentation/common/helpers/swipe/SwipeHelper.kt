package com.project.mobile_university.presentation.common.helpers.swipe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*


interface UnderlayButton {
    fun onClick(x: Float, y: Float): Boolean
    fun onDraw(canvas: Canvas, rect: RectF, position: Int, index: Int)
}

@SuppressLint("ClickableViewAccessibility")
abstract class SwipeHelper(
    context: Context,
    protected val swipeDirection: Int,
    private val recyclerView: RecyclerView,
    private val itemWidth: Int
) : ItemTouchHelper.SimpleCallback(0, swipeDirection) {

    protected open val ESCAPE_VELOCITY = 0.1f
    protected open val THRESHOLD_VELOCITY = 5f

    private var swipedPosition = -1
    private var swipeThreshold = 0.5f

    private val buttonsBuffer = hashMapOf<Int, List<UnderlayButton>>()
    private val buttons = mutableListOf<UnderlayButton>()

    private val recoverQueue = object : LinkedList<Int>() {
        override fun add(element: Int): Boolean {
            return if (contains(element)) {
                false
            } else {
                super.add(element)
            }
        }
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            buttons.forEach { button ->
                if (e != null && button.onClick(e.x, e.y))
                    return@forEach
            }

            return true
        }
    }

    private val gestureDetector = GestureDetectorCompat(context, gestureListener)

    private val onTouchListener = View.OnTouchListener { v, event ->
        if (swipedPosition < 0) return@OnTouchListener false

        val point = Point(event.x.toInt(), event.y.toInt())

        val swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPosition)
        val swipedItem = swipedViewHolder?.itemView
        val rect = Rect()
        swipedItem?.getHitRect(rect)

        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE -> {
                if (rect.top < point.y && point.y < rect.bottom) {
                    gestureDetector.onTouchEvent(event)
                } else {
                    recoverQueue.add(swipedPosition)
                    swipedPosition = -1
                    recoverSwipedItem()
                }
            }
        }

        return@OnTouchListener false
    }

    init {
        recyclerView.setOnTouchListener(onTouchListener)
        attachSwipe()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderPosition = viewHolder.adapterPosition

        if (swipedPosition != viewHolderPosition) {
            recoverQueue.add(swipedPosition)
        }

        swipedPosition = viewHolderPosition

        if (buttonsBuffer.containsKey(swipedPosition)) {
            buttons.clear()
            buttons.addAll(buttonsBuffer[swipedPosition]!!)
        } else {
            buttons.clear()
        }

        buttonsBuffer.clear()
        swipeThreshold = 0.5f * buttons.size * itemWidth
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = swipeThreshold

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return ESCAPE_VELOCITY * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return THRESHOLD_VELOCITY * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val viewHolderPosition = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView

        if (viewHolderPosition < 0) {
            swipedPosition = viewHolderPosition
            return
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                val newButtonsBuffer = mutableListOf<UnderlayButton>()

                if (!buttonsBuffer.containsKey(viewHolderPosition)) {
                    newButtonsBuffer.addAll(instantiateUnderlayButton(viewHolder))
                    buttonsBuffer[viewHolderPosition] = newButtonsBuffer
                } else {
                    buttonsBuffer[viewHolderPosition]?.let {
                        newButtonsBuffer.addAll(it)
                    }
                }

                translationX = dX * newButtonsBuffer.size * itemWidth / itemView.width
                drawButtons(c, itemView, newButtonsBuffer, viewHolderPosition, translationX)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive)
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val position = recoverQueue.poll()
            if (position > -1) {
                recyclerView.adapter?.notifyItemChanged(position)
            }
        }
    }

    private fun drawButtons(
        canvas: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>,
        position: Int,
        dX: Float
    ) {
        val buttonWidth = -1 * dX / buffer.size

        val right = itemView.right.toFloat()
        val left = right - buttonWidth
        val top = itemView.top.toFloat()
        val bottom = itemView.bottom.toFloat()

        buffer.withIndex().forEach { (index, button) ->
            button.onDraw(
                canvas = canvas,
                rect = RectF(left, top, right, bottom),
                position = position,
                index = index
            )
        }
    }

    protected fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder): List<UnderlayButton>
}