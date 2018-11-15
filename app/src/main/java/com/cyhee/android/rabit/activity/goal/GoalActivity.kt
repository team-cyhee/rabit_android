package com.cyhee.android.rabit.activity.goal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.GoalViewBinder
import com.cyhee.android.rabit.base.BaseLayoutContainer
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.util.DrawableUtil
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_reaction.*


class GoalActivity: AppCompatActivity(), GoalContract.View {

    private val TAG = GoalActivity::class.qualifiedName
    override var presenter : GoalContract.Presenter = GoalPresenter(this)
    private lateinit var goalInfo: GoalInfo

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
        this.goalInfo = goalInfo
        val contentView = (findViewById<ViewGroup>(android.R.id.content))!!.getChildAt(0)
        GoalViewBinder.bind(BaseLayoutContainer(contentView), goalInfo) { id, bool ->
            presenter.toggleLikeForGoal(id, bool)
        }
    }

    fun toggleLike(bool: Boolean) {
        Log.d(TAG, "toggleLike with $bool")
        goalInfo.liked = bool
        if(bool) {
            goalInfo.likeNum++
            likeButton.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.ic_heart_black)
            likeBtnWrapper.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.rect_sq_red)
        }
        else {
            goalInfo.likeNum--
            likeButton.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.ic_heart_outline)
            likeBtnWrapper.background = DrawableUtil.getDrawable(likeBtn.context, R.drawable.rect_sq)
        }
        likeNumberText.text = goalInfo.likeNum.toString()
    }
}