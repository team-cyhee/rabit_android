package com.cyhee.android.rabit.activity.goallogwrite

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goallogwrite.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*


class GoalLogWriteActivity: AppCompatActivity(), GoalLogWriteContract.View {
    override var presenter : GoalLogWriteContract.Presenter = GoalLogWritePresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallogwrite)

        var goalId: Long = -1
        if (intent.hasExtra("goalId")) {
            goalId = intent.getLongExtra("goalId", -1)
            val content = intent.getStringExtra("content")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayListOf(content))
            goalsNameList.adapter = spinnerAdapter
            goalsNameList.isEnabled = false
        } else {
            presenter.goalNames()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        realGoalLogPostBtn.setOnClickListener{
            val content = goalLogContentText.text.toString()
            val goalLog = GoalLogFactory.Post(content)

            if (goalId != (-1).toLong()) {
                presenter.postGoalLog(goalId, goalLog)
            } else {
                val selectedGoal = goalsNameList.selectedItem as Goal
                presenter.postGoalLog(selectedGoal.id, goalLog)
            }

        }
    }

    override fun showGoalNames(goals: MutableList<Goal>?) {
        if (goals == null) {
            //TODO: 작동안함
            val noGoal: Array<String> = arrayOf("새로운 토끼를 잡아보세요")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, noGoal)
            goalsNameList.adapter = spinnerAdapter
            goalsNameList.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goalsNameList.adapter = spinnerAdapter
        }
    }
}