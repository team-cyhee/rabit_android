package com.cyhee.android.rabit.activity.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.api.resource.RabitUrl
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.GoalLogInfo
import com.cyhee.android.rabit.model.ReportType
import com.cyhee.android.rabit.useful.Fun
import com.cyhee.android.rabit.util.DrawableUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_goallogwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.text.SimpleDateFormat

object GoalLogViewBinder {

    private val TAG = GoalLogViewBinder::class.qualifiedName
    private val baseUrl = "${RabitUrl.resourceUrl()}/rest/v1/files"

    @SuppressLint("SimpleDateFormat")
    fun bind(holder: LayoutContainer, item: GoalLogInfo, likeListener: (Long, Boolean) -> Unit, deleteGoalLog: (Long) -> Unit, reportGoalLog: (Long, ReportType) -> Unit) {
        val user = App.prefs.user

        with(holder) {
            if (item.author.username == user) {
                edit_goal_log.visibility = View.VISIBLE
            }

            name_text.text = item.goal.author.username
            create_date.text = SimpleDateFormat("MM.dd.yyy").format(item.createDate)
            val goalTitle = item.goal.content + Fun.dateDistance(item)
            title_text.text = goalTitle

            comment_number_text.text = item.companionNum.toString()
            text.text = item.content

            like_number_text.text = item.likeNum.toString()
            comment_number_text.text = item.commentNum.toString()

            val isMy = user == item.author.username
            name_text.setOnClickListener(IntentListener.toWhichWallListListener(isMy, item.author.username))
            title_text.setOnClickListener(IntentListener.toGoalListener(item.goal.id))
            text_layout.setOnClickListener(IntentListener.toGoalLogListener(item.id))
            comment_number_text.setOnClickListener(IntentListener.toGoalLogCommentsListener(item.id))
            like_number_text.setOnClickListener(IntentListener.toGoalLogLikeListListener(item.id))
            edit_goal_log.setOnClickListener(IntentListener.toGoalLogEditListener(item.id, item.goal.content, item.content))
            com_number_text.setOnClickListener(IntentListener.toCompanionListListener(item.goal.id))

            edit_goal_log.setOnClickListener{
                val pm = PopupMenu(edit_goal_log.context, edit_goal_log)
                pm.menuInflater.inflate(R.menu.edit_delete, pm.menu)

                pm.setOnMenuItemClickListener {menu ->
                    when (menu.itemId) {
                        R.id.edit -> {
                            IntentListener.toGoalLogEdit(item.id, item.goal.content, item.content, edit_goal_log.context)
                            true
                        }
                        R.id.delete -> {
                            DialogHandler.checkDialog("캐럿 삭제하기", "정말 삭제하시겠어요?", edit_goal_log.context, item.id, deleteGoalLog)
                            true
                        }
                        R.id.report -> {
                            MaterialDialog.Builder(holder.containerView!!.context)
                                    .title(R.string.report)
                                    .items(R.array.report_type)
                                    .itemsCallback { _, _, position, _ ->
                                        when(position) {
                                            0 -> reportGoalLog(item.id, ReportType.INSULT)
                                            1 -> reportGoalLog(item.id, ReportType.PORN)
                                            2 -> reportGoalLog(item.id, ReportType.INAPT)
                                            3 -> reportGoalLog(item.id, ReportType.ETC)
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

            cmt_post_btn.setOnClickListener(IntentListener.toGoalLogCommentsListener(item.id))
            when (user) {
                // TODO: 이미 companion이면 버튼 안보이게
                item.author.username -> co_btn.setOnClickListener(IntentListener.toGoalLogWriteListener(item.goal.id, item.goal.content))
                else -> co_btn.setOnClickListener(IntentListener.toCompanionWriteListener(item.goal.id, item.goal.content, item.goal.doUnit, item.goal.doTimes))
            }

            if(log_image != null && item.file.isNotEmpty()) {
                Log.d(TAG, "$baseUrl/${item.file.first().id}")
                log_image.visibility = View.VISIBLE
                Glide.with(holder.containerView).load("$baseUrl/${item.file.first().id}").into(log_image)
            }
            else {
                if(log_image != null)
                    log_image.visibility = View.GONE
            }

            Log.d("ViewHolder", item.toString())
        }
    }
}