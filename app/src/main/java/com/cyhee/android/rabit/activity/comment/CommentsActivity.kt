package com.cyhee.android.rabit.activity.comment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.activity.decoration.CardItemDeco
import kotlinx.android.synthetic.main.activity_commentlist.*
import kotlinx.android.synthetic.main.item_part_commentwrite.*
import java.lang.Exception


class CommentsActivity: AppCompatActivity(), CommentsContract.View {

    override var presenter : CommentsContract.Presenter = CommentsPresenter(this)
    private var commentsAdapter: CommentViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commentlist)

        if (intent.hasExtra("id")) {
            val id = intent.getLongExtra("id", -1)
            val type = intent.getStringExtra("type")
            when(type) {
                "goal" -> presenter.commentsForGoal(id)
                "goallog" -> presenter.commentsForGoalLog(id)
                else -> throw Exception("잘못된 댓글 요청입니다.")
            }
            // swipe refresh
            commentSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@CommentsActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                commentsAdapter?.clear()
                when(type) {
                    "goal" -> presenter.commentsForGoal(id)
                    "goallog" -> presenter.commentsForGoalLog(id)
                    else -> throw Exception("잘못된 댓글 요청입니다.")
                }
            }
        }
    }

    override fun showComments(comments: MutableList<Comment>, id: Long, type: ContentType) {
        if (commentsAdapter == null) {
            commentsAdapter = when (type) {
                ContentType.GOAL -> CommentViewAdapter(id, comments) { goalId, comment -> presenter.postCommentForGoal(goalId, comment)}
                ContentType.GOALLOG -> CommentViewAdapter(id, comments) { goalLogId, comment -> presenter.postCommentForGoalLog(goalLogId, comment)}
                else -> throw Exception("잘못된 타입에 대한 댓글 포스트 요청 입니다")
            }
            commentListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            commentListView.adapter = commentsAdapter
        } else {
            commentsAdapter!!.appendComments(comments)
        }

        commentListView.addItemDecoration(CardItemDeco(this))
        commentSwipeRefresh?.isRefreshing = false
    }

    fun addComment(comment: Comment) {
        commentsAdapter!!.appendComments(listOf(comment))
        postingCommentText.text.clear()
    }
}