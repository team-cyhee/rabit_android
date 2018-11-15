package com.cyhee.android.rabit.activity.goal

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import java.text.SimpleDateFormat


class GoalActivity: AppCompatActivity(), GoalContract.View {
    override var presenter : GoalContract.Presenter = GoalPresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.goalInfos(goalId)
        } else {
            Toast.makeText(this, "전달된 goal 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showGoalInfos(goalInfo: GoalInfo) {
        nameText.text = goalInfo.author.username
        titleText.text = goalInfo.content

        // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
        comNumberText.text = goalInfo.companionNum.toString()
        startDateText.text = when {
            goalInfo.startDate != null -> "시작일 ${SimpleDateFormat("dd/MM/yyyy").format(goalInfo.startDate)}"
            else -> "시작일 없음"
        }
        endDateText.text = when {
            goalInfo.endDate != null -> "종료일 ${SimpleDateFormat("dd/MM/yyyy").format(goalInfo.endDate)}"
            else -> "종료일 없음"
        }
        logNumText.text = goalInfo.logNum.toString()

        when (user) {
            goalInfo.author.username -> coBtn.text = "당근먹기"
            else -> coBtn.text = "함께하기"
        }
        likeNumberText.text = goalInfo.likeNum.toString()
        commentNumberText.text = goalInfo.commentNum.toString()

        likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalInfo.id))

        comNumberText.setOnClickListener(IntentListener.toCompanionListListener(goalInfo.id))

        when (user) {
            // TODO: 이미 companion이면 버튼 안보이게
            goalInfo.author.username -> coBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id, goalInfo.content))
            else -> coBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id, goalInfo.content))
        }

        val isMy = user == goalInfo.author.username
        nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalInfo.author.username))

        // like
        if (goalInfo.liked)
            likeButton.background = if (Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.ic_heart_black)
            else
                likeButton.context.resources.getDrawable(R.drawable.ic_heart_black)
        else
            likeButton.background = if (Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.ic_heart_outline)
            else
                likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)

        // post like
        likeBtn.setOnClickListener {
            goalInfo.liked = !goalInfo.liked
            presenter.toggleLikeForGoal(goalInfo.id, goalInfo.liked)

            if (goalInfo.liked)
                likeButton.background = if (Build.VERSION.SDK_INT >= 21)
                    likeButton.context.getDrawable(R.drawable.ic_heart_black)
                else
                    likeButton.context.resources.getDrawable(R.drawable.ic_heart_black)
            else
                likeButton.background = if (Build.VERSION.SDK_INT >= 21)
                    likeButton.context.getDrawable(R.drawable.ic_heart_outline)
                else
                    likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)
        }
        cmtPostBtn.setOnClickListener(IntentListener.toGoalCommentsListener(goalInfo.id))
    }
}