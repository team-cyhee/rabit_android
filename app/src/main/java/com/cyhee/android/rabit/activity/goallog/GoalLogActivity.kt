package com.cyhee.android.rabit.activity.goallog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.GoalLogViewBinder
import com.cyhee.android.rabit.base.BaseLayoutContainer
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.util.DrawableUtil
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_reaction.*


class GoalLogActivity: AppCompatActivity(), GoalLogContract.View {

    private val TAG = GoalLogActivity::class.qualifiedName
    override var presenter : GoalLogContract.Presenter = GoalLogPresenter(this)
    private lateinit var goalLogInfo: GoalLogInfo

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
    }

    override fun showGoalLogInfo(goalLogInfo : GoalLogInfo) {
        this.goalLogInfo = goalLogInfo
        val contentView = (findViewById<ViewGroup>(android.R.id.content))!!.getChildAt(0)
        GoalLogViewBinder.bind(BaseLayoutContainer(contentView), goalLogInfo) { id, bool ->
            presenter.toggleLikeForGoalLog(id, bool)
        }
    }

    fun toggleLike(bool: Boolean) {
        Log.d(TAG, "toggleLike with $bool")
        goalLogInfo.liked = bool
        if(bool) {
            goalLogInfo.likeNum++
            likeButton.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.ic_heart_black)
            likeBtnWrapper.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.rect_sq_red)
        }
        else {
            goalLogInfo.likeNum--
            likeButton.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.ic_heart_outline)
            likeBtnWrapper.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.rect_sq)
        }
        likeNumberText.text = goalLogInfo.likeNum.toString()
    }
}