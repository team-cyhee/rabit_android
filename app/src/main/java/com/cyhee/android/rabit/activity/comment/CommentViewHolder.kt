package com.cyhee.android.rabit.activity.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.base.BaseViewHolder

class CommentWriteViewHolder(parent: ViewGroup)
    :BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_part_commentwrite, parent, false))

class CommentViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_part_commentl, parent, false))