package com.cyhee.android.rabit.activity.noticelist

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_noticetitle.*


class NoticeListViewAdapter (
        private val noticeTitles: MutableList<Notice>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder = NoticeListViewHolder(parent)


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        noticeTitles[position].let { notice ->
            with(holder) {
                noticeTitle.text = notice.title
                noticeTitle.setOnClickListener(IntentListener.toNoticeListener(notice.id))
            }
        }
    }

    override fun getItemCount(): Int = noticeTitles.size

    fun appendNoticeTitles(moreNoticeTitles: List<Notice>) {
        val index = this.noticeTitles.size
        noticeTitles.addAll(moreNoticeTitles)
        notifyItemRangeInserted(index, noticeTitles.size)
    }
}
