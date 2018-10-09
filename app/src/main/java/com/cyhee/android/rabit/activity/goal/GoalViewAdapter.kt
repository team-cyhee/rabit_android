package com.cyhee.android.rabit.activity.goal

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.model.Goal
import kotlinx.android.synthetic.main.item_goal.*

class GoalViewAdapter (
    private val goals: MutableList<Goal>
) : RecyclerView.Adapter<GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder = GoalViewHolder(parent)

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        goals[position].let { goal ->
            with(holder) {
                titleText.text = goal.content
                //TODO map model to view
                Log.d("ViewHolder", goal.toString())
            }
        }
    }

    override fun getItemCount(): Int = goals.size

    fun appendGoals(moreGoals: List<Goal>) {
        val index = this.goals.size
        goals.addAll(moreGoals)
        notifyItemRangeInserted(index, goals.size)
    }
}
