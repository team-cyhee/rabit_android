package com.cyhee.android.rabit.activity.personlist.followinglist

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


class FollowingListActivity: AppCompatActivity(), FollowingListContract.View {

    override var presenter : FollowingListContract.Presenter = FollowingListPresenter(this)
    private var personViewAdapter: PersonViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.followees(username)
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        myWallBtn.setOnClickListener(IntentListener.toMyWallListener(user))
    }

    override fun showFollowees(followees: MutableList<User>) {
        if (personViewAdapter == null) {
            personViewAdapter = PersonViewAdapter(followees)
            likeListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            likeListLayout.findViewById<RecyclerView>(R.id.listView).adapter = personViewAdapter
        } else {
            personViewAdapter!!.appendPeople(followees)
        }
    }

}