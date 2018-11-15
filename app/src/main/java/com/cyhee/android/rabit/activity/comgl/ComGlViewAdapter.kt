package com.cyhee.android.rabit.activity.comgl

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.GoalLogViewBinder
import com.cyhee.android.rabit.activity.main.MainViewHolderForGoalLog
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_comg.*
import kotlinx.android.synthetic.main.item_complete_comgl.*
import kotlinx.android.synthetic.main.item_part_comglwriter.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*


class ComGlViewAdapter (
        private val comGls: MutableList<GoalLogInfo>,
        private val toggleLikeForGoalLog: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val TAG = ComGlViewAdapter::class.qualifiedName
    private val user = App.prefs.user

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder{
        return when (viewType) {
            0 -> GoalSelectViewHolder(parent)
            1 -> ComGlViewHolder(parent)
            else -> throw Exception("잘못된 컴골뷰요청")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                with (holder as GoalSelectViewHolder) {
                    comGlText.text = "명이 함께하는 중"
                }
            }
            1 -> {
                val goalLogInfo = comGls[position-1]
                GoalLogViewBinder.bind(holder as ComGlViewHolder, goalLogInfo, toggleLikeForGoalLog)
            }
        }
    }

    override fun getItemCount(): Int = comGls.size + 1

    fun appendComGls(moreComGls: List<GoalLogInfo>) {
        val index = this.comGls.size
        Log.d("ViewHolder", "index is $index in appendMainInfos")
        comGls.addAll(moreComGls)
        notifyItemRangeInserted(index, comGls.size)
    }

    fun toggleLike(id: Long, boolean: Boolean) {
        Log.d(TAG, "toggleLike $id, $boolean")
        this.comGls.forEachIndexed { index, info ->
            if (info.id == id) {
                info.liked = boolean

                if(boolean) info.likeNum++
                else info.likeNum--

                Log.d(TAG, "$index changed")
                notifyItemChanged(index + 1)
            }
        }
    }

    fun clear() {
        val size = this.comGls.size
        Log.d("ViewHolder", "size is $size in clear")
        this.comGls.clear()
        //notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
