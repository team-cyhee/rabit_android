package com.cyhee.android.rabit.activity.main

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.GoalLogViewBinder
import com.cyhee.android.rabit.activity.base.GoalViewBinder
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_mainwrite.*
import kotlinx.android.synthetic.main.item_complete_mywall.*
import kotlinx.android.synthetic.main.item_complete_wall.*
import java.lang.Exception


class MainViewAdapter (
        private val page: Int,
        private val mainInfos: MutableList<MainInfo>,
        private val wallInfo: WallInfo?,
        private val toggleLikeForGoal: (Long, Boolean) -> Unit,
        private val toggleLikeForGoalLog: (Long, Boolean) -> Unit,
        private val sendFollow: (String) -> Unit,
        private val deleteGoal: (Long) -> Unit,
        private val deleteGoalLog: (Long) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val TAG = MainViewAdapter::class.qualifiedName
    private val user = App.prefs.user
    private val headerSize = 1

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
                        main_write_btn.setOnClickListener(IntentListener.toGoalLogWriteListener())
                    }
                }
                1 -> {
                    with(holder as MyWallViewHolder) {
                        my_wall_edit_info_btn.setOnClickListener(IntentListener.toInfoEditListener(user))
                        my_wall_post_goal_btn.setOnClickListener(IntentListener.toGoalWriteListener())
                        my_wall_post_goal_log_btn.setOnClickListener(IntentListener.toGoalLogWriteListener())
                        my_wall_following_text.setOnClickListener(IntentListener.toFollowingListListener(user))
                        my_wall_follower_text.setOnClickListener(IntentListener.toFollowerListListener(user))

                        my_wall_name_text.text = wallInfo!!.username
                        my_wall_following_text.text = wallInfo.followeeNum.toString()
                        my_wall_follower_text.text = wallInfo.followerNum.toString()
                    }
                }
                2 -> {
                    with(holder as WallViewHolder) {
                        wall_goal_list_btn.setOnClickListener(IntentListener.toGoalListListener(wallInfo!!.username))
                        wall_following_text.setOnClickListener(IntentListener.toFollowingListListener(wallInfo.username))
                        wall_follower_text.setOnClickListener(IntentListener.toFollowerListListener(wallInfo.username))
                        wall_follow_btn.setOnClickListener {
                            sendFollow(wallInfo.username)
                        }

                        wall_name_text.text = wallInfo.username
                        wall_following_text.text = wallInfo.followeeNum.toString()
                        wall_follower_text.text = wallInfo.followerNum.toString()
                    }
                }
            }
            1 -> {
                val goalInfo: GoalInfo = mainInfos[position-1] as GoalInfo
                GoalViewBinder.bind(holder as MainViewHolderForGoal, goalInfo, toggleLikeForGoal, deleteGoal)
            }
            2 -> {
                val goalLogInfo: GoalLogInfo = mainInfos[position-1] as GoalLogInfo
                GoalLogViewBinder.bind(holder as MainViewHolderForGoalLog, goalLogInfo, toggleLikeForGoalLog, deleteGoalLog)
            }
        }
    }

    override fun getItemCount(): Int = mainInfos.size + headerSize

    fun appendMainInfos(moreMainInfos: List<MainInfo>) {
        val index = this.mainInfos.size
        Log.d("ViewHolder", "index is $index in appendMainInfos")
        mainInfos.addAll(moreMainInfos)
        notifyItemRangeInserted(index + headerSize, mainInfos.size)
    }

    fun toggleLike(id: Long, type: ContentType, boolean: Boolean) {
        Log.d(TAG, "toggleLike $id, $type, $boolean")
        this.mainInfos.forEachIndexed { index, info ->
            if ((type == ContentType.GOAL && info is GoalInfo && info.id == id) ||
                    (type == ContentType.GOALLOG && info is GoalLogInfo && info.id == id)) {
                info.liked = boolean

                if(boolean) info.likeNum++
                else info.likeNum--

                Log.d(TAG, "$index changed")
                notifyItemChanged(index + headerSize)
            }
        }
    }

    fun clear() {
        val size = this.mainInfos.size
        Log.d(TAG, "size is $size in clear")
        this.mainInfos.clear()
        notifyItemRangeRemoved(headerSize, size)
    }
}
