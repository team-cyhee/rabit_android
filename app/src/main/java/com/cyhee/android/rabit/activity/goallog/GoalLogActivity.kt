package com.cyhee.android.rabit.activity.goallog

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*


class GoalLogActivity: AppCompatActivity(), GoalLogContract.View {
    override var presenter : GoalLogContract.Presenter = GoalLogPresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallog)

        if (intent.hasExtra("goalLogId")) {
            val goalLogId = intent.getLongExtra("goalLogId", -1)
            presenter.goalLogInfo(goalLogId)

            likeNumberText.setOnClickListener(IntentListener.toGoalLogLikeListListener(goalLogId))

        } else {
            Toast.makeText(this, "전달된 goalLog 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showGoalLogInfo(goalLogInfo : GoalLogInfo) {
        nameText.text = goalLogInfo.goal.author.username
        val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
        titleText.text = goalTitle

        comNumberText.text = goalLogInfo.companionNum.toString()
        likeNumberText.text = goalLogInfo.likeNum.toString()
        commentNumberText.text = goalLogInfo.commentNum.toString()

        text.text = goalLogInfo.content

        comNumberText.setOnClickListener(IntentListener.toCompanionListListener(goalLogInfo.goal.id))

        val isMy = user == goalLogInfo.author.username
        nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))

        toggleLike(goalLogInfo.liked)
        likeBtn.setOnClickListener {
            goalLogInfo.liked = !goalLogInfo.liked
            presenter.toggleLikeForGoalLog(goalLogInfo.id, goalLogInfo.liked)
        }
        cmtPostBtn.setOnClickListener(IntentListener.toGoalLogCommentsListener(goalLogInfo.id))
        when (user) {
            // TODO: 이미 companion이면 버튼 안보이게
            goalLogInfo.author.username -> coBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalLogInfo.goal.id, goalLogInfo.goal.content))
            else -> coBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalLogInfo.goal.id, goalLogInfo.goal.content))
        }
    }

    fun toggleLike(on : Boolean) {
        if(on)
            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.ic_heart_black)
            else
                likeButton.context.resources.getDrawable(R.drawable.ic_heart_black)
        else
            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.ic_heart_outline)
            else
                likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)
    }
}