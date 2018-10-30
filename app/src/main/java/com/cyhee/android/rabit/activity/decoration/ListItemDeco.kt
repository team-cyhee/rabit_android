package com.cyhee.android.rabit.activity.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ListItemDeco(c: Context) : RecyclerView.ItemDecoration() {

    private val offset: Int = 10

    init {

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        when (position) {
            0 -> outRect.set(0, 0, 0, 0)
            1 -> outRect.set(0, 0, 0, offset)
            else -> outRect.set(0, offset, 0, offset)
        }
    }

}