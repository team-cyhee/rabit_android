package com.cyhee.android.rabit.listener

import android.content.Intent
import android.util.Log
import android.view.View
import com.cyhee.android.rabit.activity.goal.GoalActivity
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity
import com.cyhee.android.rabit.activity.likelist.LikeListActivity

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

    fun toGoalLikeListListener(id: Long) = View.OnClickListener {
        val intentToGoalLikeList = Intent(it.context, LikeListActivity::class.java)
        intentToGoalLikeList.putExtra("goalId", id)
        it.context.startActivity(intentToGoalLikeList)
    }

    fun toGoalLogLikeListListener(id: Long) = View.OnClickListener {
        val intentToGoalLogLikeList = Intent(it.context, LikeListActivity::class.java)
        intentToGoalLogLikeList.putExtra("goalLogId", id)
        it.context.startActivity(intentToGoalLogLikeList)
    }

    fun toGoalLogWriteListener(id: Long) = View.OnClickListener {
        Log.d("로그쓰기","로그쓰는 페이지로 이동")
    }

    fun toCompanionWriteListener(id: Long) = View.OnClickListener {
        Log.d("함께하기", "함께하는 페이지로 이동")
    }
}