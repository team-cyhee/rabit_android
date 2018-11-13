package com.cyhee.android.rabit.listener

import android.content.Intent
import android.util.Log
import android.view.View
import com.cyhee.android.rabit.activity.comgl.ComGlActivity
import com.cyhee.android.rabit.activity.comment.CommentsActivity
import com.cyhee.android.rabit.activity.personlist.followerlist.FollowerListActivity
import com.cyhee.android.rabit.activity.personlist.followinglist.FollowingListActivity
import com.cyhee.android.rabit.activity.goal.GoalActivity
import com.cyhee.android.rabit.activity.goallist.GoalListActivity
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity
import com.cyhee.android.rabit.activity.goallogwrite.GoalLogWriteActivity
import com.cyhee.android.rabit.activity.goalwrite.GoalWriteActivity
import com.cyhee.android.rabit.activity.personlist.likelist.LikeListActivity
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.mywall.MyWallActivity
import com.cyhee.android.rabit.activity.personlist.companionlist.CompanionListActivity
import com.cyhee.android.rabit.activity.search.SearchActivity
import com.cyhee.android.rabit.activity.wall.WallActivity
import com.cyhee.android.rabit.model.ContentType

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
        println("----$isMy---$username")
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

    fun toComGoalLogListener(id: Long) = View.OnClickListener {
        val intentToComGoalLog = Intent(it.context, ComGlActivity:: class.java)
        intentToComGoalLog.putExtra("goalId", id)
        it.context.startActivity(intentToComGoalLog)
    }

    fun toSearchListener() = View.OnClickListener {
        val intentToSearch = Intent(it.context, SearchActivity:: class.java)
        it.context.startActivity(intentToSearch)
    }

    fun toGoalCommentsListener(id: Long) = View.OnClickListener {
        val intentToComments = Intent(it.context, CommentsActivity:: class.java)
        intentToComments.putExtra("id", id)
        intentToComments.putExtra("type", "goal")
        it.context.startActivity(intentToComments)
    }

    fun toGoalLogCommentsListener(id: Long) = View.OnClickListener {
        val intentToComments = Intent(it.context, CommentsActivity:: class.java)
        intentToComments.putExtra("id", id)
        intentToComments.putExtra("type", "goallog")
        it.context.startActivity(intentToComments)
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

    fun toCompanionListListener(id: Long) = View.OnClickListener {
        val intentToCompanionList = Intent(it.context, CompanionListActivity::class.java)
        intentToCompanionList.putExtra("goalId", id)
        it.context.startActivity(intentToCompanionList)
    }

    fun toGoalLogWriteListener(id: Long, content: String) = View.OnClickListener {
        val intentToGoalLogWrite = Intent(it.context, GoalLogWriteActivity::class.java)
        intentToGoalLogWrite.putExtra("goalId", id)
        intentToGoalLogWrite.putExtra("content", content)
        it.context.startActivity(intentToGoalLogWrite)
    }

    fun toGoalLogWriteListener() = View.OnClickListener {
        val intentToGoalLogWrite = Intent(it.context, GoalLogWriteActivity::class.java)
        it.context.startActivity(intentToGoalLogWrite)
    }

    fun toCompanionWriteListener(id: Long, content: String) = View.OnClickListener {
        val intentToCompanionWrite = Intent(it.context, GoalWriteActivity::class.java)
        intentToCompanionWrite.putExtra("parent", id)
        intentToCompanionWrite.putExtra("content", content)
        it.context.startActivity(intentToCompanionWrite)
    }

    fun toGoalWriteListener() = View.OnClickListener {
        val intentToGoalWrite = Intent(it.context, GoalWriteActivity::class.java)
        it.context.startActivity(intentToGoalWrite)
    }

    fun toInfoEditListener(username: String) = View.OnClickListener {
        Log.d("회원정보수정", "회원정보 수정 페이지로 이동")
    }
}