package com.cyhee.android.rabit.useful

import com.cyhee.android.rabit.model.GoalLogInfo

object Fun {
    fun dateDistance(goalLog: GoalLogInfo): String {
        val startDate: Long? = (goalLog.goal.createDate).time / (24 * 60 * 60 * 1000)
        val createDate: Long? = (goalLog.createDate).time / (24 * 60 * 60 * 1000)
        val distance: Int? = (createDate!! - startDate!!).toInt() + 1
        return " $distance 일차"
    }
}