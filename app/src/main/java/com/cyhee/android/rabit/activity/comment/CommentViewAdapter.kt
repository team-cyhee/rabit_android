package com.cyhee.android.rabit.activity.comment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.Comment
import kotlinx.android.synthetic.main.item_part_comment.*

class CommentViewAdapter (
    private val comments: MutableList<Comment>
) : RecyclerView.Adapter<CommentViewHolder>() {

    private val user = App.prefs.user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder = CommentViewHolder(parent)

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        comments[position].let { comment ->
            with(holder) {
                commentWriterText.text = comment.author.username
                commentText.text = comment.content

                val isMy = user == comment.author.username
                commentWriterText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, comment.author.username))
                Log.d("ViewHolder", comment.toString())
            }
        }
    }

    override fun getItemCount(): Int = comments.size

    fun appendComments(moreGoals: List<Comment>) {
        val index = this.comments.size
        comments.addAll(moreGoals)
        notifyItemRangeInserted(index, comments.size)
    }
}
