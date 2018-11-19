package com.cyhee.android.rabit.activity.base

import android.util.Log
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
import com.cyhee.android.rabit.api.resource.RabitUrl
import com.facebook.internal.Utility


object GoalViewBinder {

    private val TAG = GoalViewBinder::class.qualifiedName
    private val baseUrl = "${RabitUrl.resourceUrl()}/rest/v1/files"

    fun bind(holder: LayoutContainer, item: GoalInfo, likeListener: (Long, Boolean) -> Unit) {
        val user = App.prefs.user

        with(holder) {
            nameText.text = item.author.username
            titleText.text = item.content

            // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
            comNumberText.text = item.companionNum.toString()

            // TODO: 나중에 시작일 의무화
            startDateText.text = when {
                item.startDate != null -> "시작일 ${SimpleDateFormat("dd/MM/yyyy").format(item.startDate)}"
                else -> "시작일 없음"
            }
            endDateText.text = when {
                item.endDate != null -> "종료일 ${SimpleDateFormat("dd/MM/yyyy").format(item.endDate)}"
                else -> "종료일 없음"
            }
            logNumText.text = item.logNum.toString()

            when (user) {
                item.author.username -> coBtn.text = "당근먹기"
                else -> coBtn.text = "함께하기"
            }

            likeNumberText.text = item.likeNum.toString()
            commentNumberText.text = item.commentNum.toString()

            val isMy = user == item.author.username
            nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, item.author.username))
            titleText.setOnClickListener(IntentListener.toGoalListener(item.id))
            logNum.setOnClickListener(IntentListener.toGoalListener(item.id))
            commentNumberText.setOnClickListener(IntentListener.toGoalCommentsListener(item.id))
            likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(item.id))

            comNumberText.setOnClickListener(IntentListener.toCompanionListListener(item.id))

            // 함께하기 /
            when (user) {
                // TODO: 이미 companion이면 버튼 안보이게
                item.author.username -> coBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(item.id, item.content))
                else -> coBtn.setOnClickListener(IntentListener.toCompanionWriteListener(item.id, item.content))
            }

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

            cmtPostBtn.setOnClickListener(IntentListener.toGoalCommentsListener(item.id))

            if(goalImage != null && !Utility.isNullOrEmpty(item.file)) {
                Glide.with(holder.containerView).load("$baseUrl/${item.file.first().id}").into(goalImage)
            }

            Log.d("ViewHolder", item.toString())
        }
    }
}