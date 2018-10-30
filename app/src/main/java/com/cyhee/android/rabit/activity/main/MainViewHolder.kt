package com.cyhee.android.rabit.activity.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.base.BaseViewHolder

class MainViewHolderForWrite(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_mainwrite, parent, false))

class MainViewHolderForGoal(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_maingoal, parent, false))

class MainViewHolderForGoalLog(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_maingoallog, parent, false))

class MyWallViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_mywall, parent, false))

class WallViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complete_wall, parent, false))
