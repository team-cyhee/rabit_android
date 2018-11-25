package com.cyhee.android.rabit.activity.personlist.likelist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.personlist.person.PersonViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_likelist.*


class LikeListActivity: AppCompatActivity(), LikeListContract.View {

    override var presenter : LikeListContract.Presenter = LikeListPresenter(this)
    private var personViewAdapter: PersonViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        when {
            intent.hasExtra("goalId") -> {
                val goalId = intent.getLongExtra("goalId", -1)
                presenter.likesForGoal(goalId)

            }
            intent.hasExtra("goalLogId") -> {
                val goalLogId = intent.getLongExtra("goalLogId", -1)
                presenter.likesForGoalLog(goalLogId)
            }
            else -> Toast.makeText(this, "전달된 goal/goalLog 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLikes(likers: MutableList<User>) {
        if (personViewAdapter == null) {
            personViewAdapter = PersonViewAdapter(likers)
            like_list_layout.findViewById<RecyclerView>(R.id.list_view).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            like_list_layout.findViewById<RecyclerView>(R.id.list_view).adapter = personViewAdapter
        } else {
            personViewAdapter!!.appendPeople(likers)
        }
    }

}