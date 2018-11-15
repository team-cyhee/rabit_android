package com.cyhee.android.rabit.activity.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.base.BaseViewHolder

class SearchPersonViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_part_person, parent, false))

class SearchItemViewHolder(parent: ViewGroup)
    : BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_part_listitem, parent, false))