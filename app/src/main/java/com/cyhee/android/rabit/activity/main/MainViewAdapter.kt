package com.cyhee.android.rabit.activity.main

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_maingoal.*
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.lang.Exception
import android.R.attr.data
import android.text.method.TextKeyListener.clear



class MainViewAdapter (
    private val mainInfos: MutableList<MainInfo>,
    private val sendLikeForGoal: (Long) -> Unit,
    private val sendLikeForGoalLog: (Long) -> Unit,
    private val sendCommentForGoal: (Long, CommentFactory.Post) -> Unit,
    private val sendCommentForGoalLog: (Long, CommentFactory.Post) -> Unit
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
        when (holder.itemViewType) {
            0 -> {
                mainInfos[position].let { mainInfo ->
                    with(holder as MainViewHolderForGoal) {
                        var goalInfo: GoalInfo = mainInfo as GoalInfo
                        nameText.text = goalInfo.author.username
                        titleText.text = goalInfo.content

                        var companion = when {
                            goalInfo.parent != null -> "${goalInfo.parent!!.author.username} 님 외 ${goalInfo.companionNum}명이 함께하는 중"
                            goalInfo.companionNum != 0 -> "${goalInfo.companionNum}명이 함께하는 중"
                            else -> "함께 해보세요!"
                        }

                        // 함께하는 사람, 좋아요 수, 댓글 수
                        companionText.text = companion
                        likeNumberText.text = goalInfo.likeNum.toString()
                        commentNumberText.text = goalInfo.commentNum.toString()

                        // 댓글 2개
                        if (goalInfo.comments.content.size > 0) {
                            commentGoalLayout1.findViewById<TextView>(R.id.commentWriterText).text = goalInfo.comments.content[0].author.username
                            commentGoalLayout1.findViewById<TextView>(R.id.commentText).text = goalInfo.comments.content[0].content
                            commentGoalLayout1.visibility = View.VISIBLE
                        } else {
                            commentGoalLayout1.visibility = View.GONE
                        }

                        if (goalInfo.comments.content.size > 1) {
                            commentGoalLayout2.findViewById<TextView>(R.id.commentWriterText).text = goalInfo.comments.content[1].author.username
                            commentGoalLayout2.findViewById<TextView>(R.id.commentText).text = goalInfo.comments.content[1].content
                            commentGoalLayout2.visibility = View.VISIBLE
                        } else {
                            commentGoalLayout2.visibility = View.GONE
                        }


                        titleText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                        commentNumberText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                        commentGoalLayout1.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                        commentGoalLayout2.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                        likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalInfo.id))

                        when {
                            goalInfo.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.parent!!.id))
                            else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                        }

                        // post like
                        likeButton.setOnClickListener {
                            sendLikeForGoal(goalInfo.id)
                        }

                        commentGoalWriteLayout.findViewById<EditText>(R.id.commentText).text.clear()

                        // post comment
                        commentGoalWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
                            val content = commentGoalWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
                            // TODO: 내용이 없을 경우 포스트 안되도록
                            val postedComment = CommentFactory.Post(content)

                            sendCommentForGoal(goalInfo.id, postedComment)
                        }

                        Log.d("ViewHolder", goalInfo.toString())
                    }
                }
            }
            1 -> {
                mainInfos[position].let { mainInfo ->
                    with(holder as MainViewHolderForGoalLog) {
                        val goalLogInfo: GoalLogInfo = mainInfo as GoalLogInfo
                        nameText.text = goalLogInfo.goal.author.username
                        val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
                        titleText.text = goalTitle

                        var companion = when {
                            goalLogInfo.goal.parent != null -> "${goalLogInfo.goal.parent!!.author.username} 님 외 ${goalLogInfo.companionNum}명이 함께하는 중"
                            goalLogInfo.companionNum != 0 -> "${goalLogInfo.companionNum}명이 함께하는 중"
                            else -> "함께 해보세요!"
                        }

                        companionText.text = companion
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

                        titleText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
                        textLayout.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                        commentGoalLogLayout1.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                        commentGoalLogLayout2.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                        commentNumberText.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                        likeNumberText.setOnClickListener(IntentListener.toGoalLogLikeListListener(goalLogInfo.id))

                        when {
                            goalLogInfo.goal.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.parent!!.id))
                            else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
                        }

                        // post like
                        likeButton.setOnClickListener {
                            sendLikeForGoalLog(goalLogInfo.id)
                        }

                        commentGoalLogWriteLayout.findViewById<EditText>(R.id.commentText).text.clear()

                        // post comment
                        commentGoalLogWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
                            val content = commentGoalLogWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
                            // TODO: 내용이 없을 경우 포스트 안되도록
                            val postedComment = CommentFactory.Post(content)

                            sendCommentForGoalLog(goalLogInfo.id, postedComment)
                        }


                        Log.d("ViewHolder", goalLogInfo.toString())
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int = mainInfos.size

    fun appendMainInfos(moreMainInfos: List<MainInfo>) {
        val index = this.mainInfos.size
        Log.d("ViewHolder", "index is $index in appendMainInfos")
        mainInfos.addAll(moreMainInfos)
        notifyItemRangeInserted(index, mainInfos.size)
    }

    fun clear() {
        val size = this.mainInfos.size
        Log.d("ViewHolder", "size is $size in clear")
        this.mainInfos.clear()
        //notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
