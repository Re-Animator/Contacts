package com.reanimator.contacts

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

private const val OFFSET = 20

class ContactItemDecorator(
    private val dividerDrawable: Drawable,
) : RecyclerView.ItemDecoration() {

    private val dividerHeight = dividerDrawable.intrinsicHeight

    override fun getItemOffsets(
        outRect: Rect,
        view: android.view.View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = OFFSET
            left = OFFSET
            right = OFFSET
            bottom = OFFSET
        }
    }

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val left = OFFSET
        val right = parent.width - OFFSET

        for (i in 0..< parent.childCount) {
            val child = parent.getChildAt(i)
            val top = child.bottom + OFFSET
            val bottom = top + dividerHeight

            dividerDrawable.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }
    }
}