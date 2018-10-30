package com.cyhee.android.rabit.activity.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView

class CardItemDeco(c: Context) : RecyclerView.ItemDecoration() {

    private val paintGray: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paintGray.color = Color.rgb(204, 204, 204)
        paintGray.style = Paint.Style.STROKE
        paintGray.strokeWidth = 10f
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val layoutManager = parent.layoutManager

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            c.drawRect(
                    (layoutManager!!.getDecoratedLeft(child)).toFloat(),
                    (layoutManager.getDecoratedTop(child)).toFloat(),
                    (layoutManager.getDecoratedRight(child)).toFloat(),
                    (layoutManager.getDecoratedBottom(child)).toFloat(),
                    paintGray)

        }

    }
}