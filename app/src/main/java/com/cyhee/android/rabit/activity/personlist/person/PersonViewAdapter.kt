package com.cyhee.android.rabit.activity.personlist.person

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_part_person.*

class PersonViewAdapter (
    private val people: MutableList<User>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder = PersonViewHolder(parent)


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        people[position].let { person ->
            with(holder) {
                name_text.text = person.username
                Log.d("ViewHolder", person.toString())
            }
        }
    }


    override fun getItemCount(): Int = people.size

    fun appendPeople(morePeople: List<User>) {
        val index = this.people.size
        people.addAll(morePeople)
        notifyItemRangeInserted(index, people.size)
    }
}
