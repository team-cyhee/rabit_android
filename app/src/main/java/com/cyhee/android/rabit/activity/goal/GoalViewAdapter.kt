package com.cyhee.android.rabit.activity.goal

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_cardgoal.*
import kotlinx.android.synthetic.main.item_complete_maingoal.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import java.text.SimpleDateFormat

class GoalViewAdapter (
    private val goalInfos: MutableList<GoalInfo>
) : RecyclerView.Adapter<GoalViewHolder>() {

    private val user = App.prefs.user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder = GoalViewHolder(parent)

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        goalInfos[position].let { goalInfo ->
            with(holder) {
                goalCard.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))

                cardGoalName.text = goalInfo.content

                cardGoalDateText.text = Fun.dateDistance(goalInfo.startDate)

                cardGoalComBtn.text = when (user) {
                    goalInfo.author.username -> "당근먹기"
                    else -> "함께하기"
                }

                // 함께하기
                when (user) {
                    // TODO: 이미 companion이면 버튼 안보이게
                    goalInfo.author.username -> cardGoalComBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id, goalInfo.content))
                    else -> cardGoalComBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id, goalInfo.content))
                }

                Log.d("ViewHolder", goalInfo.toString())
            }
        }
    }

    override fun getItemCount(): Int = goalInfos.size

    fun appendGoalInfos(moreGoalInfos: List<GoalInfo>) {
        val index = this.goalInfos.size
        goalInfos.addAll(moreGoalInfos)
        notifyItemRangeInserted(index, goalInfos.size)
    }

    fun clear() {
        val size = this.goalInfos.size
        Log.d("ViewHolder", "size is $size in clear")
        this.goalInfos.clear()
        //notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
