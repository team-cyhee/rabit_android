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
            goalContentText.isEnabled = false
        } else if (intent.hasExtra("goalId")) {
            goalId = intent.getLongExtra("goalId", -1)
            realGoalPostBtn.text = "수정"
        }

        if (parent != null || goalId != null) {
            goalContentText.setText(intent.getStringExtra("content"))

            if (intent.hasExtra("unit") && intent.hasExtra("times")) {
                goalRadioGroup.findViewById<RadioButton>(resources.getIdentifier
                ("${intent.getStringExtra("unit").toString().toLowerCase()}Btn", "id", packageName)).isChecked = true
                for (i in 0 until goalRadioGroup.childCount) {
                    goalRadioGroup.getChildAt(i).isEnabled = false
                }
                goalTimeText.setText(intent.getIntExtra("times", -1).toString())
                goalTimeText.isEnabled = false
            }

            if (intent.hasExtra("startDate")) {
                goalStartText.setText(intent.getStringExtra("startDate"))
            }
            if (intent.hasExtra("endDate")) {
                goalStartText.setText(intent.getStringExtra("endDate"))
            }

            goalContentText.setTextColor(Color.rgb(1,1,1))
        }

        var radio: RadioButton = noneBtn
        goalRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            radio = findViewById(checkedId)
            if (radio.text == "매일") {
                goalTimeText.setText("1")
            } else {
                goalTimeText.text = null
            }
        }

        realGoalPostBtn.setOnClickListener{
            val unit = when (radio.text) {
                "매일" -> GoalUnit.DAILY
                "주별" -> GoalUnit.WEEKLY
                "월별" -> GoalUnit.MONTHLY
                "년별" -> GoalUnit.YEARLY
                else -> null
            }
            val times =  goalTimeText.text.toString().toInt()
            val content = goalContentText.text.toString()
            // TODO: 날짜 받아오기
            val startDate = Date(System.currentTimeMillis())

            val goal = GoalFactory.Post(content, startDate, null, unit, times)

            if (parent != null) {
                presenter.postCompanion(parent, goal)
            } else if (goalId != null) {
                presenter.editGoal(goalId, goal)
            } else {
                presenter.postGoal(goal)
            }

        }
    }
}