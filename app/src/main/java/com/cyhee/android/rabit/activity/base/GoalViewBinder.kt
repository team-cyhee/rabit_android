package com.cyhee.android.rabit.activity.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import com.afollestad.materialdialogs.MaterialDialog
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.GoalInfo
import com.cyhee.android.rabit.util.DrawableUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import java.text.SimpleDateFormat
import com.bumptech.glide.Glide
import com.cyhee.android.rabit.activity.goal.GoalPresenter
import com.cyhee.android.rabit.api.resource.RabitUrl
import com.cyhee.android.rabit.model.ReportType
import com.facebook.internal.Utility

object GoalViewBinder {

    private val TAG = GoalViewBinder::class.qualifiedName
    private val baseUrl = "${RabitUrl.resourceUrl()}/rest/v1/files"

    @SuppressLint("SimpleDateFormat")
    fun bind(holder: LayoutContainer, item: GoalInfo, likeListener: (Long, Boolean) -> Unit, deleteGoal: (Long) -> Unit, reportGoal: (Long, ReportType) -> Unit) {
        val user = App.prefs.user

        with(holder) {
            if (item.author.username == user) {
                edit_goal.visibility = View.VISIBLE
            }

            if (profile_image != null && item.author.files.isNotEmpty()) {
                Glide.with(holder.containerView).load("$baseUrl/${item.author.files[item.author.files.size-1].id}").into(profile_image)
            }
            name_text.text = item.author.username
            title_text.text = item.content

            create_date.text = SimpleDateFormat("MM.dd.yyy").format(item.lastUpdated)
            // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
            com_number_text.text = item.companionNum.toString()

            // TODO: 나중에 시작일 의무화
            start_date_text.text = when {
                item.startDate != null -> "시작일 ${SimpleDateFormat("dd/MM/yyyy").format(item.startDate)}"
                else -> "시작일 없음"
            }
            end_date_text.text = when {
                item.endDate != null -> "종료일 ${SimpleDateFormat("dd/MM/yyyy").format(item.endDate)}"
                else -> "종료일 없음"
            }
            log_num_text.text = item.logNum.toString()

            when (user) {
                item.author.username -> co_btn.text = "당근먹기"
                else -> co_btn.text = "함께하기"
            }

            like_number_text.text = item.likeNum.toString()
            comment_number_text.text = item.commentNum.toString()

            val isMy = user == item.author.username
            name_text.setOnClickListener(IntentListener.toWhichWallListListener(isMy, item.author.username))
            title_text.setOnClickListener(IntentListener.toGoalListener(item.id))
            log_num.setOnClickListener(IntentListener.toGoalListener(item.id))
            comment_number_text.setOnClickListener(IntentListener.toGoalCommentsListener(item.id))
            like_number_text.setOnClickListener(IntentListener.toGoalLikeListListener(item.id))
            com_number_text.setOnClickListener(IntentListener.toCompanionListListener(item.id))

            edit_goal.setOnClickListener{
                val pm = PopupMenu(edit_goal.context, edit_goal)
                pm.menuInflater.inflate(R.menu.edit_delete, pm.menu)

                pm.setOnMenuItemClickListener {menu ->
                    when (menu.itemId) {
                        R.id.edit -> {
                            IntentListener.toGoalEdit(item.id, item.content, item.doUnit, item.doTimes, item.startDate, item.endDate, edit_goal.context)
                            true
                        }
                        R.id.delete -> {
                            DialogHandler.checkDialog("래빗 삭제하기", "정말 삭제하시겠어요?", edit_goal.context, item.id, deleteGoal)
                            true
                        }
                        R.id.report -> {
                            MaterialDialog.Builder(holder.containerView!!.context)
                                    .title(R.string.report)
                                    .items(R.array.report_type)
                                    .itemsCallback { _, _, position, _ ->
                                        when(position) {
                                            0 -> reportGoal(item.id, ReportType.INSULT)
                                            1 -> reportGoal(item.id, ReportType.PORN)
                                            2 -> reportGoal(item.id, ReportType.INAPT)
                                            3 -> reportGoal(item.id, ReportType.ETC)
                                        }
                                    }
                                    .negativeText(R.string.reset)
                                    .show()
                            true
                        }
                        else -> false
                    }
                }
                pm.show()
            }

            // 함께하기 /
            when (user) {
                // TODO: 이미 companion이면 버튼 안보이게
                item.author.username -> co_btn.setOnClickListener(IntentListener.toGoalLogWriteListener(item.id, item.content))
                else -> co_btn.setOnClickListener(IntentListener.toCompanionWriteListener(item.id, item.content, item.doUnit, item.doTimes))
            }

            like_button.background =
                    if (item.liked) DrawableUtil.getDrawable(like_btn.context, R.drawable.ic_heart_black)
                    else DrawableUtil.getDrawable(like_btn.context, R.drawable.ic_heart_outline)

            like_btn_wrapper.background =
                    if (item.liked) DrawableUtil.getDrawable(like_btn.context, R.drawable.rect_sq_red)
                    else DrawableUtil.getDrawable(like_btn.context, R.drawable.rect_sq)

            // post like
            like_btn.setOnClickListener {
                likeListener(item.id, !item.liked)
            }

            cmt_post_btn.setOnClickListener(IntentListener.toGoalCommentsListener(item.id))

            if(goal_image != null && !Utility.isNullOrEmpty(item.files)) {
                goal_image.visibility = View.VISIBLE
                Glide.with(holder.containerView).load("$baseUrl/${item.files.first().id}").into(goal_image)
            }
            else {
                if(goal_image != null)
                    goal_image.visibility = View.GONE
            }

            Log.d("ViewHolder", item.toString())
        }
    }
}