package com.cyhee.android.rabit.activity.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_fullgoallog.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.lang.Exception

class MainViewAdapter (
    private val mainInfos: MutableList<MainInfo>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (mainInfos[position].type == ContentType.GOAL) {
            return 0
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == 0) {
            return MainViewHolderForGoal(parent)
        } else if (viewType == 1) {
            return MainViewHolderForGoalLog(parent)
        }
        throw Exception("goal 또는 goallog만 들어와야함")
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            mainInfos[position].let { mainInfo ->
                with(holder as MainViewHolderForGoal) {
                    var goalInfo: GoalInfo = mainInfo as GoalInfo
                    nameText.text = goalInfo.author.username
                    titleText.text = goalInfo.content
                    if (goalInfo.parent != null) {
                        // TODO: 사람 수 추가
                        val original = "${goalInfo.parent!!.author.username} 님 외 n명이 함께하는 중"
                        originalWriterText.text = original
                    }

                    likeNumberText.text = goalInfo.likeNum.toString()
                    commentNumberText.text = goalInfo.commentNum.toString()

                    // TODO: goalActivity 만든 뒤 수정
                    val clickListener = View.OnClickListener {
                        val intentToGoalLog = Intent(it.context, GoalLogActivity::class.java)
                        intentToGoalLog.putExtra("goalId", mainInfo.id)
                        it.context.startActivity(intentToGoalLog)
                    }

                    // TODO: 좋아요 누르기 추가
                    textLayout.setOnClickListener(clickListener)
                    commentNumberText.setOnClickListener(clickListener)

                    // TODO: comment 2개 더 추가
                    // TODO: 댓글 달기 추가

                    Log.d("ViewHolder", goalInfo.toString())
                }
            }
        } else if (holder.itemViewType == 1) {
            mainInfos[position].let { mainInfo ->
                with(holder as MainViewHolderForGoalLog) {
                    var goalLogInfo: GoalLogInfo = mainInfo as GoalLogInfo
                    nameText.text = goalLogInfo.goal.author.username
                    titleText.text = goalLogInfo.goal.content
                    if (goalLogInfo.goal.parent != null) {
                        // TODO: 사람 수 추가
                        val original = "${goalLogInfo.goal.parent!!.author.username} 님 외 n명이 함께하는 중"
                        originalWriterText.text = original
                    }
                    text.text = goalLogInfo.content

                    likeNumberText.text = goalLogInfo.likeNum.toString()
                    commentNumberText.text = goalLogInfo.commentNum.toString()

                    val clickListener = View.OnClickListener {
                        val intentToGoalLog = Intent(it.context, GoalLogActivity::class.java)
                        intentToGoalLog.putExtra("goalId", mainInfo.id)
                        it.context.startActivity(intentToGoalLog)
                    }

                    textLayout.setOnClickListener(clickListener)
                    commentNumberText.setOnClickListener(clickListener)

                    Log.d("ViewHolder", goalLogInfo.toString())
                }
            }
        }
    }


    override fun getItemCount(): Int = mainInfos.size

    fun appendMainInfos(moreMainInfos: List<MainInfo>) {
        val index = this.mainInfos.size
        mainInfos.addAll(moreMainInfos)
        notifyItemRangeInserted(index, mainInfos.size)
    }
}
