package com.cyhee.android.rabit.activity.likelist

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_part_person.*
import java.lang.Exception

class LikeListViewAdapter (
    private val likes: MutableList<Like>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeListViewHolder = LikeListViewHolder(parent)


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        likes[position].let { like ->
            with(holder) {
                nameText.text = like.author.username
                Log.d("ViewHolder", like.toString())
            }
        }
    }


    override fun getItemCount(): Int = likes.size

    fun appendLikes(moreLikes: List<Like>) {
        val index = this.likes.size
        likes.addAll(moreLikes)
        notifyItemRangeInserted(index, likes.size)
    }
}
