package com.cyhee.android.rabit.activity.wall

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.base.InfiniteScrollListener
import com.cyhee.android.rabit.activity.main.MainViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_wall.*
import kotlinx.android.synthetic.main.item_complete_usertopbar.*

class WallActivity: AppCompatActivity(), WallContract.View {

    private val TAG = WallActivity::class.qualifiedName
    override var presenter : WallContract.Presenter = WallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall)

        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username")
            presenter.wallInfo(username)
            username_text.text = username
            val layoutManager = LinearLayoutManager(this)
            wall_list_view.layoutManager = layoutManager

            search_btn.setOnClickListener(IntentListener.toSearchListener())
            to_up_btn.setOnClickListener{
                wall_list_view.smoothScrollToPosition(0)
            }

            // swipe refresh
            wall_swipe_refresh.setOnRefreshListener {
                Toast.makeText(this@WallActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                mainAdapter?.clear()
                presenter.wallInfo(username)

                wall_swipe_refresh?.isRefreshing = false
            }

            // infinite scroll
            wall_list_view.addOnScrollListener(InfiniteScrollListener(layoutManager) {
                presenter.userMainInfos(username, null,  mainAdapter!!.lastTime())
            })
        } else {
            Toast.makeText(this, "전달된 username이 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>, wallInfo: WallInfo?) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(2, mainInfos, wallInfo,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { followee: String -> presenter.postFollow(followee)},
                    { id -> presenter.deleteGoal(id)},
                    { id -> presenter.deleteGoalLog(id)},
                    { type, id, reportType -> presenter.report(type, id, reportType)})
            wall_list_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            wall_list_view.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }
    }

    fun toggleLike(id: Long, type: ContentType, boolean: Boolean) {
        Log.d(TAG, "toggle like $id, $type, $boolean")
        mainAdapter!!.toggleLike(id, type, boolean)
    }
}