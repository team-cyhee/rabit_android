package com.cyhee.android.rabit.activity.comgl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.InfiniteScrollListener
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_comgoallog.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class ComGlActivity: AppCompatActivity(), ComGlContract.View {

    override var presenter : ComGlContract.Presenter = ComGlPresenter(this)
    private var comGlAdapter: ComGlViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comgoallog)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.comGls(goalId)
            val layoutManager = LinearLayoutManager(this)
            com_gl_list_view.layoutManager = layoutManager

            search_btn.setOnClickListener(IntentListener.toSearchListener())
            to_up_btn.setOnClickListener{
                com_gl_list_view.smoothScrollToPosition(0)
            }

            // swipe refresh
            com_gl_swipe_refresh.setOnRefreshListener {
                Toast.makeText(this@ComGlActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                comGlAdapter?.clear()
                presenter.comGls(goalId)
            }

            // infinite scroll
            com_gl_list_view.addOnScrollListener(InfiniteScrollListener(layoutManager) {
                presenter.comGls(goalId, comGlAdapter!!.lastPage())
            })
        } else {
            Toast.makeText(this, "전달된 goalId가 없습니다", Toast.LENGTH_SHORT).show()
        }

        search_btn.setOnClickListener(IntentListener.toSearchListener())
    }

    override fun showComGls(comGls: MutableList<GoalLogInfo>) {
        if (comGlAdapter == null) {
            comGlAdapter = ComGlViewAdapter(comGls,
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id -> presenter.deleteGoalLog(id)},
                    { id, reportType -> presenter.report(id, reportType)})
            com_gl_list_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            com_gl_list_view.adapter = comGlAdapter
        } else {
            comGlAdapter!!.appendComGls(comGls)
        }

        com_gl_swipe_refresh?.isRefreshing = false
    }

    override fun setWriteGoalId(id: Long) {
    }

    fun toggleLike(id: Long, boolean: Boolean) {
        comGlAdapter!!.toggleLike(id, boolean)
    }
}