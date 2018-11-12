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
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import java.util.*


class GoalWriteActivity: AppCompatActivity(), GoalWriteContract.View {
    override var presenter : GoalWriteContract.Presenter = GoalWritePresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goalwrite)

        var parent: Long? = null
        if (intent.hasExtra("parent")) {
            parent = intent.getLongExtra("parent", -1)
            goalContentText.setText(intent.getStringExtra("content"))
            goalContentText.setTextColor(Color.rgb(1,1,1))
            goalContentText.isEnabled = false

        }
        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
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

            if (parent == null) {
                presenter.postGoal(goal)
            } else {
                presenter.postCompanion(parent, goal)
            }

        }
    }
}