package com.cyhee.android.rabit.activity.main

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_mainwrite.*
import kotlinx.android.synthetic.main.item_complete_topbar.*
import kotlinx.android.synthetic.main.item_part_reaction.*


class MainActivity: AppCompatActivity(), MainContract.View {

    override var presenter : MainContract.Presenter = MainPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.goalNames()
        presenter.mainInfos()

        // post goalLog
        mainWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
            val selectedGoal = mainWriteLayout.findViewById<Spinner>(R.id.goalNameList).selectedItem as Goal
            val content = mainWriteLayout.findViewById<EditText>(R.id.dailyText).text.toString()
            val postedGoalLog = GoalLogFactory.Post(content)
            // TODO: 내용이 없경우 포스트 안되도록
            presenter.postGoalLog(selectedGoal.id, postedGoalLog)
        }

        myWallBtn.setOnClickListener(IntentListener.toMyWallListener(user))

        // swipe refresh
        swipeRefresh.setOnRefreshListener {
            Toast.makeText(this@MainActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            mainAdapter?.clear()
            presenter.mainInfos()
        }
    }

    override fun showGoalNames(goals: MutableList<Goal>?) {
        if (goals == null) {
            //TODO: 작동안함
            val noGoal: Array<String> = arrayOf("새로운 토끼를 잡아보세요")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, noGoal)
            goalNameList.adapter = spinnerAdapter
            goalNameList.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goalNameList.adapter = spinnerAdapter
        }
    }

    override fun showMainInfos(mainInfos: MutableList<MainInfo>) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(mainInfos,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)})
            mainInfoListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            mainInfoListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        swipeRefresh?.isRefreshing = false
    }

    fun toggleLike(on : Boolean) {
        if(on)
            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.thumb_active)
            else
                likeButton.context.resources.getDrawable(R.drawable.thumb_active)
        else
            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.thumb)
            else
                likeButton.context.resources.getDrawable(R.drawable.thumb)
    }
}