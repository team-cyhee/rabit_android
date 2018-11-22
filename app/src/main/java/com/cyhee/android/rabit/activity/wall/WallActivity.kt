package com.cyhee.android.rabit.activity.wall

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.main.MainViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_wall.*
import kotlinx.android.synthetic.main.item_complete_usertopbar.*

class WallActivity: AppCompatActivity(), WallContract.View {

    private val TAG = WallActivity::class.qualifiedName
    override var presenter : WallContract.Presenter = WallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.wallInfo(username)
            usernameText.text = username

            searchBtn.setOnClickListener(IntentListener.toSearchListener())
            toUpBtn.setOnClickListener{
                wallListView.smoothScrollToPosition(0)
            }

            // swipe refresh
            wallSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@WallActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                mainAdapter?.clear()
                presenter.wallInfo(username)
            }
        } else {
            Toast.makeText(this, "전달된 username이 없습니다", Toast.LENGTH_SHORT).show()
        }

    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>, wallInfo: WallInfo) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(2, mainInfos, wallInfo,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { followee: String -> presenter.postFollow(followee)},
                    { id -> presenter.deleteGoal(id)},
                    { id -> presenter.deleteGoalLog(id)})
            wallListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            wallListView.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        wallSwipeRefresh?.isRefreshing = false
    }

    fun toggleLike(id: Long, type: ContentType, boolean: Boolean) {
        Log.d(TAG, "toggle like $id, $type, $boolean")
        mainAdapter!!.toggleLike(id, type, boolean)
    }
}