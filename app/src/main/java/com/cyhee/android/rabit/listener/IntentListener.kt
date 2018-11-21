package com.cyhee.android.rabit.listener

import android.annotation.SuppressLint
import android.content.Context
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
import com.cyhee.android.rabit.activity.goalloglist.GoalLogListActivity
import com.cyhee.android.rabit.activity.goallogwrite.GoalLogWriteActivity
import com.cyhee.android.rabit.activity.goalwrite.GoalWriteActivity
import com.cyhee.android.rabit.activity.personlist.likelist.LikeListActivity
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.mywall.MyWallActivity
import com.cyhee.android.rabit.activity.personlist.companionlist.CompanionListActivity
import com.cyhee.android.rabit.activity.question.QuestionActivity
import com.cyhee.android.rabit.activity.search.SearchActivity
import com.cyhee.android.rabit.activity.wall.WallActivity
import com.cyhee.android.rabit.model.ContentType
import com.cyhee.android.rabit.model.GoalUnit
import java.text.SimpleDateFormat
import java.util.*

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

    fun toComGoalLogListener(id: Long) = View.OnClickListener {
        val intentToComGoalLog = Intent(it.context, ComGlActivity:: class.java)
        intentToComGoalLog.putExtra("goalId", id)
        it.context.startActivity(intentToComGoalLog)
    }

    fun toGoalLogListListener(id: Long) = View.OnClickListener {
        val intentToGoalLogList = Intent(it.context, GoalLogListActivity:: class.java)
        intentToGoalLogList.putExtra("goalId", id)
        it.context.startActivity(intentToGoalLogList)
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

    fun toGoalLogWriteListener(id: Long, goalContent: String) = View.OnClickListener {
        val intentToGoalLogWrite = Intent(it.context, GoalLogWriteActivity::class.java)
        intentToGoalLogWrite.putExtra("goalId", id)
        intentToGoalLogWrite.putExtra("goalContent", goalContent)
        it.context.startActivity(intentToGoalLogWrite)
    }

    fun toGoalLogWriteListener() = View.OnClickListener {
        val intentToGoalLogWrite = Intent(it.context, GoalLogWriteActivity::class.java)
        it.context.startActivity(intentToGoalLogWrite)
    }

    fun toGoalLogEditListener(id: Long, goalContent: String, content: String) = View.OnClickListener {
        val intentToGoalLogWrite = Intent(it.context, GoalLogWriteActivity::class.java)
        intentToGoalLogWrite.putExtra("goalLogId", id)
        intentToGoalLogWrite.putExtra("goalContent", goalContent)
        intentToGoalLogWrite.putExtra("content", content)
        it.context.startActivity(intentToGoalLogWrite)
    }

    fun toCompanionWriteListener(id: Long, content: String, doUnit: GoalUnit?, doTimes: Int?) = View.OnClickListener {
        val intentToCompanionWrite = Intent(it.context, GoalWriteActivity::class.java)
        intentToCompanionWrite.putExtra("parent", id)
        intentToCompanionWrite.putExtra("content", content)
        if (doUnit != null) intentToCompanionWrite.putExtra("unit", doUnit.toString())
        if (doTimes != null) intentToCompanionWrite.putExtra("times", doTimes)
        it.context.startActivity(intentToCompanionWrite)
    }

    fun toGoalWriteListener() = View.OnClickListener {
        val intentToGoalWrite = Intent(it.context, GoalWriteActivity::class.java)
        it.context.startActivity(intentToGoalWrite)
    }

    @SuppressLint("SimpleDateFormat")
    fun toGoalEditListener(id: Long, content: String, doUnit: GoalUnit?, doTimes: Int?, startDate: Date?, endDate: Date?) = View.OnClickListener {
        val intentToGoalWrite = Intent(it.context, GoalWriteActivity::class.java)
        intentToGoalWrite.putExtra("goalId", id)
        intentToGoalWrite.putExtra("content", content)
        if (doUnit != null) intentToGoalWrite.putExtra("unit", doUnit.toString())
        if (doTimes != null) intentToGoalWrite.putExtra("times", doTimes)
        if (startDate != null) intentToGoalWrite.putExtra("startDate", SimpleDateFormat("MM/dd/yyy").format(startDate))
        if (endDate != null) intentToGoalWrite.putExtra("endDate",  SimpleDateFormat("MM/dd/yyy").format(endDate))
        it.context.startActivity(intentToGoalWrite)
    }

    fun toInfoEditListener(username: String) = View.OnClickListener {
        Log.d("회원정보수정", "회원정보 수정 페이지로 이동")
    }

    fun toQuestionWriteListener() = View.OnClickListener {
        val intentToQuestionWrite = Intent(it.context, QuestionActivity::class.java)
        it.context.startActivity(intentToQuestionWrite)
    }


    @SuppressLint("SimpleDateFormat")
    fun toGoalEdit(id: Long, content: String, doUnit: GoalUnit?, doTimes: Int?, startDate: Date?, endDate: Date?, context: Context) {
        val intentToGoalWrite = Intent(context, GoalWriteActivity::class.java)
        intentToGoalWrite.putExtra("goalId", id)
        intentToGoalWrite.putExtra("content", content)
        if (doUnit != null) intentToGoalWrite.putExtra("unit", doUnit.toString())
        if (doTimes != null) intentToGoalWrite.putExtra("times", doTimes)
        if (startDate != null) intentToGoalWrite.putExtra("startDate", SimpleDateFormat("MM/dd/yyy").format(startDate))
        if (endDate != null) intentToGoalWrite.putExtra("endDate",  SimpleDateFormat("MM/dd/yyy").format(endDate))
        context.startActivity(intentToGoalWrite)
    }

    fun toGoalLogEdit(id: Long, goalContent: String, content: String, context: Context)  {
        val intentToGoalLogWrite = Intent(context, GoalLogWriteActivity::class.java)
        intentToGoalLogWrite.putExtra("goalLogId", id)
        intentToGoalLogWrite.putExtra("goalContent", goalContent)
        intentToGoalLogWrite.putExtra("content", content)
        context.startActivity(intentToGoalLogWrite)
    }
}