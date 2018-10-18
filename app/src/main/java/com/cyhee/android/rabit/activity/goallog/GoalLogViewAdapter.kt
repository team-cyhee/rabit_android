package com.cyhee.android.rabit.activity.goallog

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.model.Comment
import kotlinx.android.synthetic.main.item_part_comment.*

class GoalLogViewAdapter (
    private val comments: MutableList<Comment>
) : RecyclerView.Adapter<GoalLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalLogViewHolder = GoalLogViewHolder(parent)

    override fun onBindViewHolder(holder: GoalLogViewHolder, position: Int) {
        comments[position].let { comment ->
            with(holder) {
                commentWriterText.text = comment.author.username
                commentText.text = comment.content
                Log.d("ViewHolder", comment.toString())
            }
        }
    }

    override fun getItemCount(): Int = comments.size

    fun appendComments(moreComment: List<Comment>) {
        val index = this.comments.size
        comments.addAll(moreComment)
        notifyItemRangeInserted(index, comments.size)
    }
}
