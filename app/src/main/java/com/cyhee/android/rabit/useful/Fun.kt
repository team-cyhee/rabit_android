package com.cyhee.android.rabit.useful

import com.cyhee.android.rabit.model.GoalLogInfo
import java.util.*

object Fun {

    fun dateDistance(goalLog: GoalLogInfo): String {
        val startDate: Date? = goalLog.goal.createDate
        val createDate: Date? = goalLog.createDate
        val distance: Long? = (createDate!!.time - startDate!!.time) / (24 * 60 * 60 * 1000)
        println("---$startDate~$createDate=$distance---")
        return " $distance 일차"
    }
}