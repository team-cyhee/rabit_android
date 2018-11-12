package com.cyhee.android.rabit.activity.personlist.followerlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.personlist.person.PersonViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_likelist.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*


class FollowerListActivity: AppCompatActivity(), FollowerListContract.View {

    override var presenter : FollowerListContract.Presenter = FollowerListPresenter(this)
    private var personViewAdapter: PersonViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.followers(username)
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

    }

    override fun showFollowers(followers: MutableList<User>) {
        if (personViewAdapter == null) {
            personViewAdapter = PersonViewAdapter(followers)
            likeListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            likeListLayout.findViewById<RecyclerView>(R.id.listView).adapter = personViewAdapter
        } else {
            personViewAdapter!!.appendPeople(followers)
        }
    }

}