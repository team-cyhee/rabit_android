package com.cyhee.android.rabit.listener

import android.content.Intent
import android.util.Log
import android.view.View
import com.cyhee.android.rabit.activity.personlist.followerlist.FollowerListActivity
import com.cyhee.android.rabit.activity.personlist.followinglist.FollowingListActivity
import com.cyhee.android.rabit.activity.goal.GoalActivity
import com.cyhee.android.rabit.activity.goallist.GoalListActivity
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity
import com.cyhee.android.rabit.activity.personlist.likelist.LikeListActivity
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.mywall.MyWallActivity
import com.cyhee.android.rabit.activity.wall.WallActivity

object IntentListener {
    fun toMainListener() = View.OnClickListener {
        val intentToMain = Intent(it.context, MainActivity::class.java)
        it.context.startActivity(intentToMain)
    }

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

    fun toWhichWallListListener(isMy: Boolean, username: String)  = View.OnClickListener {
        val intentToWhichWall = when (isMy) {
            true -> Intent(it.context, MyWallActivity:: class.java)
            false -> Intent(it.context, WallActivity:: class.java)
        }
        intentToWhichWall.putExtra("username", username)
        it.context.startActivity(intentToWhichWall)
    }

    fun toMyWallListener(username: String) = View.OnClickListener {
        val intentToMyWall = Intent(it.context, MyWallActivity:: class.java)
        intentToMyWall.putExtra("username", username)
        it.context.startActivity(intentToMyWall)
    }

    fun toGoalListListener(username: String) = View.OnClickListener {
        val intentToGoalList = Intent(it.context, GoalListActivity:: class.java)
        intentToGoalList.putExtra("username", username)
        it.context.startActivity(intentToGoalList)
    }

    fun toFollowingListListener(username: String) = View.OnClickListener {
        val intentToFollowingList = Intent(it.context, FollowingListActivity:: class.java)
        intentToFollowingList.putExtra("username", username)
        it.context.startActivity(intentToFollowingList)
    }

    fun toFollowerListListener(username: String) = View.OnClickListener {
        val intentToFollowerList = Intent(it.context, FollowerListActivity:: class.java)
        intentToFollowerList.putExtra("username", username)
        it.context.startActivity(intentToFollowerList)
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

    fun toGoalLogWriteListener() = View.OnClickListener {
        Log.d("로그쓰기","로그쓰는 페이지로 이동")
    }

    fun toCompanionWriteListener(id: Long) = View.OnClickListener {
        Log.d("함께하기", "함께하는 페이지로 이동")
    }

    fun toGoalWriteListener() = View.OnClickListener {
        Log.d("골쓰기","골쓰는 페이지로 이동")
    }

    fun toInfoEditListener(username: String) = View.OnClickListener {
        Log.d("회원정보수정", "회원정보 수정 페이지로 이동")
    }
}