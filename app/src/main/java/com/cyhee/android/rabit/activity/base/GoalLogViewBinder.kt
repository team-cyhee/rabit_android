package com.cyhee.android.rabit.activity.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.GoalInfo
import com.cyhee.android.rabit.model.GoalLogInfo
import com.cyhee.android.rabit.useful.Fun
import com.cyhee.android.rabit.util.DrawableUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_part_actions.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.text.SimpleDateFormat

object GoalLogViewBinder {
    @SuppressLint("SimpleDateFormat")
    fun bind(holder: LayoutContainer, item: GoalLogInfo, likeListener: (Long, Boolean) -> Unit) {
        val user = App.prefs.user

        with(holder) {
            nameText.text = item.goal.author.username
            createDate.text = SimpleDateFormat("MM/dd/yyy").format(item.createDate)
            val goalTitle = item.goal.content + Fun.dateDistance(item)
            titleText.text = goalTitle

            comNumberText.text = item.companionNum.toString()
            text.text = item.content

            likeNumberText.text = item.likeNum.toString()
            commentNumberText.text = item.commentNum.toString()

            val isMy = user == item.author.username
            nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, item.author.username))
            titleText.setOnClickListener(IntentListener.toGoalListener(item.goal.id))
            textLayout.setOnClickListener(IntentListener.toGoalLogListener(item.id))
            commentNumberText.setOnClickListener(IntentListener.toGoalLogCommentsListener(item.id))
            likeNumberText.setOnClickListener(IntentListener.toGoalLogLikeListListener(item.id))

            comNumberText.setOnClickListener(IntentListener.toCompanionListListener(item.goal.id))

            likeButton.background =
                    if (item.liked) DrawableUtil.getDrawable(likeBtn.context, R.drawable.ic_heart_black)
                    else DrawableUtil.getDrawable(likeBtn.context, R.drawable.ic_heart_outline)

            likeBtnWrapper.background =
                    if (item.liked) DrawableUtil.getDrawable(likeBtn.context, R.drawable.rect_sq_red)
                    else DrawableUtil.getDrawable(likeBtn.context, R.drawable.rect_sq)

            // post like
            likeBtn.setOnClickListener {
                likeListener(item.id, !item.liked)
            }

            cmtPostBtn.setOnClickListener(IntentListener.toGoalLogCommentsListener(item.id))
            when (user) {
                // TODO: 이미 companion이면 버튼 안보이게
                item.author.username -> coBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(item.goal.id, item.goal.content))
                else -> coBtn.setOnClickListener(IntentListener.toCompanionWriteListener(item.goal.id, item.goal.content))
            }

            Log.d("ViewHolder", item.toString())
        }
    }
}