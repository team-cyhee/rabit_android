package com.cyhee.android.rabit.activity.search

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_part_listitem.*
import kotlinx.android.synthetic.main.item_part_person.*
import java.lang.Exception

class SearchViewAdapter (
        private val type: Int,
        private val results: MutableList<SearchForm>
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val user = App.prefs.user

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            0 -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> SearchPersonViewHolder(parent)
            1 -> SearchItemViewHolder(parent)
            else -> throw Exception("올바른 페이지 접근이 아닙니다.")
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> results[position].let { result ->
                with(holder as SearchPersonViewHolder) {
                    nameText.text = result.content
                    Log.d("ViewHolder", result.toString())
                    nameText.setOnClickListener(
                            IntentListener.toWhichWallListListener(user == result.user.username, result.user.username)
                    )
                }
            }
            1 -> results[position].let { result ->
                with(holder as SearchItemViewHolder) {
                    itemNameText.text = result.content
                    Log.d("ViewHolder", result.toString())
                    itemNameText.setOnClickListener(
                            when (result.type) {
                                ContentType.GOAL -> IntentListener.toGoalListener(result.id)
                                ContentType.GOALLOG -> IntentListener.toGoalLogListener(result.id)
                                else -> throw Exception("잘못된 타입의 검색 결과입니다.")
                            }
                    )
                }
            }
        }

    }


    override fun getItemCount(): Int = results.size

    fun appendResults(moreResults: List<SearchForm>) {
        val index = this.results.size
        results.addAll(moreResults)
        notifyItemRangeInserted(index, results.size)
    }

    fun clear() {
        val size = this.results.size
        Log.d("ViewHolder", "size is $size in clear")
        this.results.clear()
        //notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
