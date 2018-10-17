package com.cyhee.android.rabit.activity.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), MainContract.View {

    override var presenter : MainContract.Presenter = MainPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

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
            // TODO: 내용이 없을 경우 포스트 안되도록
            presenter.postGoaLog(selectedGoal.id, postedGoalLog)
        }
    }

    override fun showGoalNames(goals: MutableList<Goal>) {
        val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
        mainWriteLayout.findViewById<Spinner>(R.id.goalNameList).adapter = spinnerAdapter
        //TODO: default 값 지정하기
    }

    override fun showMainInfos(mainInfos: MutableList<MainInfo>) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(mainInfos, { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)}, { id, comment -> presenter.postCommentForGoalLog(id, comment)})
            mainInfoListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            mainInfoListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }
    }

}