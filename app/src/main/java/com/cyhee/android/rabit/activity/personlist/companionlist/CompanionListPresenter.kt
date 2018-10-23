package com.cyhee.android.rabit.activity.personlist.companionlist

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class CompanionListPresenter(private val view: CompanionListActivity) : CompanionListContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun companionsForGoal(id: Long) {
        restClient.goalStoreLikes(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("companions",it.toString())
                            view.showCompanions(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("companions",it.response().toString())
                                Log.d("companions",it.response().body().toString())
                                Log.d("companions",it.response().body().toString())
                                Log.d("companions",it.response().errorBody().toString())
                                Log.d("companions",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("companions",it.toString())
                            }
                        }
                )
    }

    override fun companionsForGoalLog(id: Long) {
        restClient.goalLogStoreLikes(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("companions",it.toString())
                            view.showCompanions(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("companions",it.response().toString())
                                Log.d("companions",it.response().body().toString())
                                Log.d("companions",it.response().body().toString())
                                Log.d("companions",it.response().errorBody().toString())
                                Log.d("companions",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("companions",it.toString())
                            }
                        }
                )
    }
}