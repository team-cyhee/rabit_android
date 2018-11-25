package com.cyhee.android.rabit.activity.alarm

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.ViewGroup
import android.widget.TextView
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_alarm.*
import java.lang.Exception


class AlarmViewAdapter (
        private val alarms: MutableList<Alarm>
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val user = App.prefs.user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder = AlarmViewHolder(parent)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        alarms[position].let { alarm ->
            with(holder) {
                val color = "<font color=#3277d8>"
                var content: String? = ""

                if (alarm.author.username != alarm.owner.username) {
                    content = if (alarm.author.username != user)  "$color${alarm.author.username}</font>님이 회원님"
                        else  "$color${alarm.owner.username}</font>님"

                    if (alarm.target != null) {
                        content += "의 $color${alarm.target!!.korean}</font>"
                    }
                } else if (alarm.target != null) {
                    content = "회원님의 $color${alarm.target!!.korean}</font>"
                }

                content += when (alarm.action) {
                    ContentType.GOAL -> {
                        if (alarm.target != null) "을 $color 함께</font>합니다"
                        else "$color 래빗</font>을 작성했습니다"
                    }
                    ContentType.GOALLOG -> "에 $color 캐럿</font>을 작성했습니다"
                    ContentType.FOLLOW -> "을 $color 팔로우</font>합니다"
                    ContentType.COMMENT -> "에 $color 댓글</font>을 작성했습니다"
                    ContentType.LIKE -> "을 $color 응원</font>합니다"
                    else -> {
                        throw Exception("잘못된 컨텐츠 타입입니다.")
                    }
                }
                alarm_content.setText(Html.fromHtml(content), TextView.BufferType.SPANNABLE)

                when (alarm.action) {
                    ContentType.GOAL -> alarm_content.setOnClickListener(IntentListener.toGoalListener(alarm.actionId))
                    ContentType.GOALLOG -> alarm_content.setOnClickListener(IntentListener.toGoalLogListener(alarm.actionId))
                    ContentType.FOLLOW -> if (alarm.owner.username == user)
                        alarm_content.setOnClickListener(IntentListener.toFollowerListListener(user))
                        else alarm_content.setOnClickListener(IntentListener.toFollowingListListener(user))
                    ContentType.COMMENT, ContentType.LIKE -> {}
                    else -> {
                        throw Exception("잘못된 컨텐츠 타입입니다.")
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = alarms.size

    fun appendAlarms(moreAlarms: List<Alarm>) {
        val index = this.alarms.size
        alarms.addAll(moreAlarms)
        notifyItemRangeInserted(index, alarms.size)
    }
}
