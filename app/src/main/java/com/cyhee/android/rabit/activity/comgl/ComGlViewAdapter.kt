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
        private val toggleLikeForGoalLog: (Long, Boolean) -> Unit,
        private val deleteGoalLog: (Long) -> Unit,
        private val report: (Long, ReportType) -> Unit
) : RecyclerView.Adapter<ComGlViewHolder>() {

    private val TAG = ComGlViewAdapter::class.qualifiedName
    private val user = App.prefs.user
    private val headerSize = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComGlViewHolder = ComGlViewHolder(parent)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ComGlViewHolder, position: Int) {
        comGls[position].let { goalLogInfo ->
                GoalLogViewBinder.bind(holder, goalLogInfo, toggleLikeForGoalLog, deleteGoalLog, { id, reportType ->
                    report(id, reportType)
                })
            }
    }

    override fun getItemCount(): Int = comGls.size

    fun appendComGls(moreComGls: List<GoalLogInfo>) {
        val index = this.comGls.size
        Log.d("ViewHolder", "index is $index in appendMainInfos")
        comGls.addAll(moreComGls)
        notifyItemRangeInserted(index+headerSize, comGls.size)
    }

    fun toggleLike(id: Long, boolean: Boolean) {
        Log.d(TAG, "toggleLike $id, $boolean")
        this.comGls.forEachIndexed { index, info ->
            if (info.id == id) {
                info.liked = boolean

                if(boolean) info.likeNum++
                else info.likeNum--

                Log.d(TAG, "$index changed")
                notifyItemChanged(index+headerSize)
            }
        }
    }

    fun clear() {
        val size = this.comGls.size
        Log.d("ViewHolder", "size is $size in clear")
        this.comGls.clear()
        notifyItemRangeRemoved(headerSize, size)
    }

    fun lastPage() = this.comGls.size / 10
}
