package com.cyhee.android.rabit.activity.goalwrite

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goalwrite.*
import java.util.*


class GoalWriteActivity: AppCompatActivity(), GoalWriteContract.View {
    override var presenter : GoalWriteContract.Presenter = GoalWritePresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goalwrite)

        var parent: Long? = null
        var goalId: Long? = null
        if (intent.hasExtra("parent")) {
            parent = intent.getLongExtra("parent", -1)
            goal_content_text.isEnabled = false
        } else if (intent.hasExtra("goalId")) {
            goalId = intent.getLongExtra("goalId", -1)
            real_goal_post_btn.text = "수정"
        }

        if (parent != null || goalId != null) {
            goal_content_text.setText(intent.getStringExtra("content"))

            if (intent.hasExtra("unit") && intent.hasExtra("times")) {
                goal_radio_group.findViewById<RadioButton>(resources.getIdentifier
                ("${intent.getStringExtra("unit").toString().toLowerCase()}_btn", "id", packageName)).isChecked = true
                for (i in 0 until goal_radio_group.childCount) {
                    goal_radio_group.getChildAt(i).isEnabled = false
                }
                goal_time_text.setText(intent.getIntExtra("times", -1).toString())
                goal_time_text.isEnabled = false
            }

            if (intent.hasExtra("startDate")) {
                goal_start_text.setText(intent.getStringExtra("startDate"))
            }
            if (intent.hasExtra("endDate")) {
                goal_start_text.setText(intent.getStringExtra("endDate"))
            }

            goal_content_text.setTextColor(Color.rgb(1,1,1))
        }

        var radio: RadioButton = none_btn
        goal_radio_group.setOnCheckedChangeListener { group, checkedId ->
            radio = findViewById(checkedId)
            if (radio.text == "매일") {
                goal_time_text.setText("1")
            } else {
                goal_time_text.text = null
            }
        }

        real_goal_post_btn.setOnClickListener{
            val unit = when (radio.text) {
                "매일" -> GoalUnit.DAILY
                "주별" -> GoalUnit.WEEKLY
                "월별" -> GoalUnit.MONTHLY
                "년별" -> GoalUnit.YEARLY
                else -> null
            }
            val times =  goal_time_text.text.toString().toInt()
            val content = goal_content_text.text.toString()
            // TODO: 날짜 받아오기
            val startDate = Date(System.currentTimeMillis())

            val goal = GoalFactory.Post(content, startDate, null, unit, times)

            when {
                parent != null -> presenter.postCompanion(parent, goal)
                goalId != null -> presenter.editGoal(goalId, goal)
                else -> presenter.postGoal(goal)
            }

        }
    }
}