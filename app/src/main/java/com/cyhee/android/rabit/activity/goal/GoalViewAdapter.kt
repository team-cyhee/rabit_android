package com.cyhee.android.rabit.activity.goal

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.cyhee.android.rabit.model.Goal

class GoalViewAdapter (
    private val goals: ArrayList<Goal>
) : RecyclerView.Adapter<GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder = GoalViewHolder(parent)

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = goals.size
}
