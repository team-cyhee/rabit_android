package com.cyhee.android.rabit.activity.goal

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_part_commentwriteform.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.lang.Exception
import java.text.SimpleDateFormat

class GoalViewAdapter (
    private val goalInfo: GoalInfo,
    private val goalLogInfos: MutableList<GoalLogInfo>,
    private val sendLikeForGoalLog: (Long) -> Unit,
    private val sendCommentForGoalLog: (Long, CommentFactory.Post) -> Unit,
    private val sendLikeForGoal: (Long) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val user = App.prefs.user

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> GoalViewHolder(parent)
            1 -> GoalLogViewHolder(parent)
            else -> throw Exception("올바르지 못한 데이터 접근")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                with(holder as GoalViewHolder) {
                    nameText.text = goalInfo.author.username
                    titleText.text = goalInfo.content

                    // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
                    companionText.text = when {
                        goalInfo.parent != null -> "${goalInfo.parent!!.author.username} 님 외 ${goalInfo.companionNum}명이 함께하는 중"
                        goalInfo.companionNum != 0 -> "${goalInfo.companionNum}명이 함께하는 중"
                        else -> "함께 해보세요!"
                    }
                    startDateText.text = when {
                        goalInfo.startDate != null -> "시작일 ${SimpleDateFormat("dd/MM/yyyy").format(goalInfo.startDate)}"
                        else -> "시작일 없음"
                    }
                    endDateText.text = when {
                        goalInfo.endDate != null -> "종료일 ${SimpleDateFormat("dd/MM/yyyy").format(goalInfo.endDate)}"
                        else -> "종료일 없음"
                    }
                    logNumText.text = goalInfo.logNum.toString()

                    when (user) {
                        goalInfo.author.username -> goalBtn.text = "당근먹기"
                        else -> goalBtn.text = "함께하기"
                    }
                    likeNumberText.text = goalInfo.likeNum.toString()
                    commentNumberText.text = goalInfo.commentNum.toString()

                    likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalInfo.id))

                    when {
                        goalInfo.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.parent!!.id))
                        else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                    }

                    when (user) {
                        // TODO: 이미 companion이면 버튼 안보이게
                        goalInfo.author.username -> goalBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id, goalInfo.content))
                        else -> goalBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id, goalInfo.content))
                    }

                    val isMy = user == goalInfo.author.username
                    nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalInfo.author.username))
                    likeButton.setOnClickListener {
                        sendLikeForGoal(goalInfo.id)
                    }
                    toComment.setOnClickListener(IntentListener.toGoalCommentsListener(goalInfo.id))
                }
            }
            1 -> {
                with(holder as GoalLogViewHolder) {
                    val goalLogInfo = goalLogInfos[position-1]
                    nameText.text = goalLogInfo.goal.author.username
                    val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
                    titleText.text = goalTitle

                    val companion = when {
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

                    val isMy = user == goalLogInfo.author.username
                    nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))
                    titleText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
                    textLayout.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                    commentGoalLogLayout1.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                    commentGoalLogLayout2.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                    commentGoalLogLayout1.findViewById<TextView>(R.id.commentWriterText).setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))
                    commentGoalLogLayout2.findViewById<TextView>(R.id.commentWriterText).setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalLogInfo.author.username))
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

                    // post comment
                    commentGoalLogWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
                        val content = commentGoalLogWriteLayout.findViewById<EditText>(R.id.postingCommentText).text.toString()
                        // TODO: 내용이 없을 경우 포스트 안되도록
                        val postedComment = CommentFactory.Post(content)
                        sendCommentForGoalLog(goalLogInfo.id, postedComment)
                    }


                    Log.d("ViewHolder", goalLogInfo.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int = goalLogInfos.size + 1

    fun appendGoalLogInfos(moreGoalLogInfos: List<GoalLogInfo>) {
        val index = this.goalLogInfos.size
        goalLogInfos.addAll(moreGoalLogInfos)
        notifyItemRangeInserted(index, goalLogInfos.size)
    }

    fun clear() {
        val size = this.goalLogInfos.size
        Log.d("ViewHolder", "size is $size in clear")
        this.goalLogInfos.clear()
        notifyDataSetChanged()
    }
}
