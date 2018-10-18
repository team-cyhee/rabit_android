package com.cyhee.android.rabit.listener

import android.content.Intent
import android.view.View
import com.cyhee.android.rabit.activity.goal.GoalActivity
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity

object IntentListener {
    fun toGoalListener(id: Long) = View.OnClickListener {
        val intentToGoal = Intent(it.context, GoalActivity::class.java)
        intentToGoal.putExtra("goalId", id)
        it.context.startActivity(intentToGoal)
    }

    fun toGoalLogListener(id: Long) = View.OnClickListener {
        val intentToGoalLog = Intent(it.context, GoalLogActivity::class.java)
        intentToGoalLog.putExtra("goalLogId", id)
        it.context.startActivity(intentToGoalLog)
    }
}