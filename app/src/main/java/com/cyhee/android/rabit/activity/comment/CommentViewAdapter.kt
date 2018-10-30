package com.cyhee.android.rabit.activity.comment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.CommentFactory
import kotlinx.android.synthetic.main.item_part_comment.*
import kotlinx.android.synthetic.main.item_part_commentwrite.*
import java.lang.Exception

class CommentViewAdapter (
        private val id: Long,
        private val comments: MutableList<Comment>,
        private val sendComment: (Long, CommentFactory.Post) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val user = App.prefs.user

    override fun getItemViewType(position: Int): Int {
        val len = comments.size
        return when (position) {
            len -> 0
            else -> 1
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> CommentWriteViewHolder(parent)
            1 -> CommentViewHolder(parent)
            else -> throw Exception("잘못된 댓글 요청")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> with(holder as CommentWriteViewHolder) {
                postBtn.setOnClickListener{
                    val comment = postingCommentText.text.toString()
                    sendComment(id, CommentFactory.Post(comment))
                }
            }
            1 -> with(holder as CommentViewHolder) {
                val comment = comments[position]

                commentWriterText.text = comment.author.username
                commentText.text = comment.content

                val isMy = user == comment.author.username
                commentWriterText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, comment.author.username))
                Log.d("ViewHolder", comment.toString())
            }
        }
    }

    override fun getItemCount(): Int = comments.size + 1

    fun appendComments(moreGoals: List<Comment>) {
        val index = this.comments.size
        comments.addAll(moreGoals)
        notifyItemRangeInserted(index, comments.size)
    }

    fun clear() {
        val size = this.comments.size
        Log.d("ViewHolder", "size is $size in clear")
        this.comments.clear()
        //notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
