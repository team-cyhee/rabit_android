package com.cyhee.android.rabit.activity.goallist

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_cardgoal.*

class GoalListViewAdapter (
    private val goalInfos: MutableList<GoalInfo>
) : RecyclerView.Adapter<GoalListViewHolder>() {

    private val user = App.prefs.user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalListViewHolder = GoalListViewHolder(parent)

    override fun onBindViewHolder(holder: GoalListViewHolder, position: Int) {
        goalInfos[position].let { goalInfo ->
            with(holder) {
                goalCard.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))

                cardGoalName.text = goalInfo.content

/*                cardGoalComBtn.text = when (user) {
                    goalInfo.author.username -> "당근먹기"
                    else -> "함께하기"
                }*/

                // 함께하기
                when (user) {
                    // TODO: 이미 companion이면 버튼 안보이게
                    goalInfo.author.username -> cardGoalComBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id, goalInfo.content))
                    else -> cardGoalComBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id, goalInfo.content))
                }

                //cardGoalLogBtn.setOnClickListener(IntentListener.toGoalLogListListener(goalInfo.id))
                cardGoalComLogBtn.setOnClickListener(IntentListener.toComGoalLogListener(goalInfo.id))

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
