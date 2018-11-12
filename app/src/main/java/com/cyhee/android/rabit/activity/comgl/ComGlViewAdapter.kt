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
        private val spinner: ArrayAdapter<GoalInfo>,
        private val comGls: MutableList<GoalLogInfo>,
        private val toggleLikeForGoalLog: (Long, Boolean) -> Unit,
        private val sendCommentForGoalLog: (Long, CommentFactory.Post) -> Unit,
        private val selectGoal: (Long) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

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
                    goalNameList.adapter = spinner
                    selectGoalBtn.setOnClickListener {
                        val selectedGoal = goalNameList.selectedItem as GoalInfo
                        comGlText.text = "${selectedGoal.companionNum+1}명이 함께하는 중"
                        selectGoal(selectedGoal.id)
                    }
                }
            }
            1 -> {
                with(holder as ComGlViewHolder) {
                    val goalLogInfo: GoalLogInfo = comGls[position-1]
                    nameText.text = goalLogInfo.goal.author.username
                    val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
                    titleText.text = goalTitle

                    text.text = goalLogInfo.content

                    likeNumberText.text = goalLogInfo.likeNum.toString()
                    commentNumberText.text = goalLogInfo.commentNum.toString()

                    if (goalLogInfo.comments.content.size > 0) {
                        commentGoalLogLayout1.findViewById<TextView>(R.id.commentWriterText).text = goalLogInfo.comments.content[0].author.username
                        commentGoalLogLayout1.findViewById<TextView>(R.id.commentText).text = goalLogInfo.comments.content[0].content
                        commentGoalLogLayout1.visibility = View.VISIBLE
                    } else {
                        commentGoalLogLayout1.visibility = View.GONE
                    }

                    if (goalLogInfo.comments.content.size > 1) {
                        commentGoalLogLayout2.findViewById<TextView>(R.id.commentWriterText).text = goalLogInfo.comments.content[1].author.username
                        commentGoalLogLayout2.findViewById<TextView>(R.id.commentText).text = goalLogInfo.comments.content[1].content
                        commentGoalLogLayout2.visibility = View.VISIBLE
                    } else {
                        commentGoalLogLayout2.visibility = View.GONE
                    }

                    val isMy = user == goalLogInfo.author.username
                    nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))
                    titleText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
                    textLayout.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                    commentGoalLogLayout1.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                    commentGoalLogLayout2.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                    commentGoalLogLayout1.findViewById<TextView>(R.id.commentWriterText).setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))
                    commentGoalLogLayout2.findViewById<TextView>(R.id.commentWriterText).setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))
                    commentNumberText.setOnClickListener(IntentListener.toGoalLogCommentsListener(goalLogInfo.id))
                    likeNumberText.setOnClickListener(IntentListener.toGoalLogLikeListListener(goalLogInfo.id))

                    if (goalLogInfo.liked) {
                        likeButton.background = if (Build.VERSION.SDK_INT >= 21)
                            likeButton.context.getDrawable(R.drawable.ic_heart_black)
                        else
                            likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)
                    }
                    // post like
                    likeBtn.setOnClickListener {
                        goalLogInfo.liked = !goalLogInfo.liked
                        toggleLikeForGoalLog(goalLogInfo.id, goalLogInfo.liked)

                        if (goalLogInfo.liked) {
                            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                                likeButton.context.getDrawable(R.drawable.ic_heart_black)
                            else
                                likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)
                        }
                    }

                    cmtPostBtn.setOnClickListener(IntentListener.toGoalLogCommentsListener(goalLogInfo.id))
                    when (user) {
                        // TODO: 이미 companion이면 버튼 안보이게
                        goalLogInfo.author.username -> coBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalLogInfo.goal.id, goalLogInfo.goal.content))
                        else -> coBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalLogInfo.goal.id, goalLogInfo.goal.content))
                    }

                    Log.d("ViewHolder", goalLogInfo.toString())
                }
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

    fun clear() {
        val size = this.comGls.size
        Log.d("ViewHolder", "size is $size in clear")
        this.comGls.clear()
        //notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
