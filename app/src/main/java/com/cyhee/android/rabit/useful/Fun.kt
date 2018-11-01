package com.cyhee.android.rabit.useful

import android.widget.Button
import com.cyhee.android.rabit.model.GoalLogInfo
import java.util.*

object Fun {
    fun dateDistance(goalLog: GoalLogInfo): String {
        val startDate: Long? = (goalLog.goal.startDate).time / (24 * 60 * 60 * 1000)
        val createDate: Long? = (goalLog.createDate).time / (24 * 60 * 60 * 1000)
        val distance: Int? = (createDate!! - startDate!!).toInt() + 1
        return " $distance 일차"
    }

    fun dateDistance(start: Date): String {
        val startDate: Long = start.time / (24 * 60 * 60 * 1000)
        val today: Long = Calendar.getInstance().time.time / (24 * 60 * 60 * 1000)
        val distance: Int = (today - startDate).toInt() + 1
        return " $distance 일차"
    }
}
