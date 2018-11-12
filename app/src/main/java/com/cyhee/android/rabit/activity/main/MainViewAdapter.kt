package com.cyhee.android.rabit.activity.main

import android.annotation.SuppressLint
import android.os.Build
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
import kotlinx.android.synthetic.main.item_complete_maingoal.*
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_complete_mainwrite.*
import kotlinx.android.synthetic.main.item_complete_mywall.*
import kotlinx.android.synthetic.main.item_complete_wall.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.lang.Exception
import java.text.SimpleDateFormat


class MainViewAdapter (
        private val page: Int,
        private val mainInfos: MutableList<MainInfo>,
        private val wallInfo: WallInfo?,
        private val toggleLikeForGoal: (Long, Boolean) -> Unit,
        private val toggleLikeForGoalLog: (Long, Boolean) -> Unit,
        private val sendFollow: (String) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val user = App.prefs.user

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        }
        return when (mainInfos[position-1].type) {
            ContentType.GOAL -> 1
            ContentType.GOALLOG -> 2
            else -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> when (page) {
                0 -> MainViewHolderForWrite(parent)
                1 -> MyWallViewHolder(parent)
                2 -> WallViewHolder(parent)
                else -> throw Exception("올바른 페이지 접근이 아닙니다.")
            }
            1 -> MainViewHolderForGoal(parent)
            2 -> MainViewHolderForGoalLog(parent)
            else -> throw Exception("goal 또는 goallog만 들어와야함")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> when (page) {
                0 -> {
                    with(holder as MainViewHolderForWrite) {
                        mainWriteBtn.setOnClickListener(IntentListener.toGoalLogWriteListener())
                    }
                }
                1 -> {
                    with(holder as MyWallViewHolder) {
                        myWallEditInfoBtn.setOnClickListener(IntentListener.toInfoEditListener(user))
                        myWallPostGoalBtn.setOnClickListener(IntentListener.toGoalWriteListener())
                        myWallPostGoalLogBtn.setOnClickListener(IntentListener.toGoalLogWriteListener())
                        myWallFollowingText.setOnClickListener(IntentListener.toFollowingListListener(user))
                        myWallFollowerText.setOnClickListener(IntentListener.toFollowerListListener(user))
                        myWallGoalsText.setOnClickListener(IntentListener.toGoalListListener(user))

                        myWallNameText.text = wallInfo!!.username
                        myWallFollowingText.text = "${wallInfo.followeeNum} 팔로잉"
                        myWallFollowerText.text = "${wallInfo.followerNum} 팔로워"
                        if (wallInfo.goalContents.isNotEmpty())
                            myWallGoal1Text.text = wallInfo.goalContents[0]
                        if (wallInfo.goalContents.size > 1)
                            myWallGoal2Text.text = wallInfo.goalContents[1]
                        if (wallInfo.goalContents.size > 2)
                            myWallGoal3Text.text = wallInfo.goalContents[2]
                    }
                }
                2 -> {
                    with(holder as WallViewHolder) {
                        wallFollowingText.setOnClickListener(IntentListener.toFollowingListListener(wallInfo!!.username))
                        wallFollowerText.setOnClickListener(IntentListener.toFollowerListListener(wallInfo.username))
                        wallGoalsText.setOnClickListener(IntentListener.toGoalListListener(wallInfo.username))
                        wallFollowBtn.setOnClickListener {
                            sendFollow(user)
                        }

                        wallNameText.text = wallInfo.username
                        wallFollowingText.text = "${wallInfo.followeeNum} 팔로잉"
                        wallFollowerText.text = "${wallInfo.followerNum} 팔로워"
                        if (wallInfo.goalContents.isNotEmpty())
                            wallGoal1Text.text = wallInfo.goalContents[0]
                        if (wallInfo.goalContents.size > 1)
                            wallGoal2Text.text = wallInfo.goalContents[1]
                        if (wallInfo.goalContents.size > 2)
                            wallGoal3Text.text = wallInfo.goalContents[2]
                    }
                }
            }
            1 -> {
                with(holder as MainViewHolderForGoal) {
                    val goalInfo: GoalInfo = mainInfos[position-1] as GoalInfo
                    nameText.text = goalInfo.author.username
                    titleText.text = goalInfo.content

                    // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
                    comNumberText.text = goalInfo.companionNum.toString()

                    // TODO: 나중에 시작일 의무화
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
                        goalInfo.author.username -> coBtn.text = "당근먹기"
                        else -> coBtn.text = "함께하기"
                    }

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

                    val isMy = user == goalInfo.author.username
                    nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalInfo.author.username))
                    titleText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                    logNum.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                    commentNumberText.setOnClickListener(IntentListener.toGoalCommentsListener(goalInfo.id))
                    commentGoalLayout1.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                    commentGoalLayout2.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                    commentGoalLayout1.findViewById<TextView>(R.id.commentWriterText).setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalInfo.author.username))
                    commentGoalLayout2.findViewById<TextView>(R.id.commentWriterText).setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalInfo.author.username))
                    likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalInfo.id))

                    comNumberText.setOnClickListener(IntentListener.toCompanionListListener(goalInfo.id))

                    // 함께하기 /
                    when (user) {
                        // TODO: 이미 companion이면 버튼 안보이게
                        goalInfo.author.username -> coBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id, goalInfo.content))
                        else -> coBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id, goalInfo.content))
                    }

                    if (goalInfo.liked) {
                        likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                            likeButton.context.getDrawable(R.drawable.ic_heart_black)
                        else
                            likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)
                    }
                    // post like
                    likeBtn.setOnClickListener {
                        goalInfo.liked = !goalInfo.liked
                        toggleLikeForGoal(goalInfo.id, goalInfo.liked)

                        if (goalInfo.liked) {
                            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                                likeButton.context.getDrawable(R.drawable.ic_heart_black)
                            else
                                likeButton.context.resources.getDrawable(R.drawable.ic_heart_outline)
                        }
                    }

                    cmtPostBtn.setOnClickListener(IntentListener.toGoalCommentsListener(goalInfo.id))

                    Log.d("ViewHolder", goalInfo.toString())
                }
            }
            2 -> {
                with(holder as MainViewHolderForGoalLog) {
                    val goalLogInfo: GoalLogInfo = mainInfos[position-1] as GoalLogInfo
                    nameText.text = goalLogInfo.goal.author.username
                    val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
                    titleText.text = goalTitle

                    comNumberText.text = goalLogInfo.companionNum.toString()
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

                    comNumberText.setOnClickListener(IntentListener.toCompanionListListener(goalLogInfo.goal.id))

                    if (goalLogInfo.liked) {
                        likeButton.background = if(Build.VERSION.SDK_INT >= 21)
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

    override fun getItemCount(): Int = mainInfos.size + 1

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
