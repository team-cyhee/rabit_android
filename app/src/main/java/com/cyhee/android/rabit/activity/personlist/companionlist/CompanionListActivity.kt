package com.cyhee.android.rabit.activity.personlist.companionlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.personlist.person.PersonViewAdapter
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_companionlist.*


class CompanionListActivity: AppCompatActivity(), CompanionListContract.View {

    override var presenter : CompanionListContract.Presenter = CompanionListPresenter(this)
    private var personViewAdapter: PersonViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companionlist)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.companionsForGoal(goalId)
        }
    }

    override fun showCompanions(companions: MutableList<User>) {
        if (personViewAdapter == null) {
            personViewAdapter = PersonViewAdapter(companions)
            companionListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            companionListLayout.findViewById<RecyclerView>(R.id.listView).adapter = personViewAdapter
        } else {
            personViewAdapter!!.appendPeople(companions)
        }
    }

}