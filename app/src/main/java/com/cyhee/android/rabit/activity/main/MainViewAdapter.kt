package com.cyhee.android.rabit.activity.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity
import com.cyhee.android.rabit.model.GoalLog
import kotlinx.android.synthetic.main.item_complete_fullgoallog.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_text.*

// TODO: goal도 불러오도록 수정
// TODO: goalLog text 매핑하는 부분 리팩토링
class MainViewAdapter (
    private val goalLogs: MutableList<GoalLog>
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        goalLogs[position].let { goalLog ->
            with(holder) {
                println(goalLog.content)
                nameText.text = goalLog.goal.author.username
                titleText.text = goalLog.goal.content
                if (goalLog.goal.parent != null) {
                    // 사람 수 추가
                    val original = "${goalLog.goal.parent.author.username} 님 외 n명이 함께하는 중"
                    originalWriterText.text = original
                }

                text.text = goalLog.content

                // TODO: get # of likes and comments

                textLayout.setOnClickListener {
                    val intentToGoalLog = Intent(it.context, GoalLogActivity::class.java)
                    intentToGoalLog.putExtra("goalLogId", goalLog.id)
                    it.context.startActivity(intentToGoalLog)
                }

                Log.d("ViewHolder", goalLog.toString())
            }
        }
    }

    override fun getItemCount(): Int = goalLogs.size

    fun appendGoalLogs(moreGoalLogs: List<GoalLog>) {
        val index = this.goalLogs.size
        goalLogs.addAll(moreGoalLogs)
        notifyItemRangeInserted(index, goalLogs.size)
    }
}
