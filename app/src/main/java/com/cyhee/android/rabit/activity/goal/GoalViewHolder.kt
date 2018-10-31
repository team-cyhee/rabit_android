package com.cyhee.android.rabit.activity.goal

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.base.BaseViewHolder

class GoalViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_fullgoal, parent, false))

class GoalLogViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_maingoallog, parent, false))
