package com.cyhee.android.rabit.activity.personlist.followerlist

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class FollowerListPresenter(private val view: FollowerListActivity) : FollowerListContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun followers(username: String) {
        restClient.followers(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("followers",it.toString())
                            view.showFollowers(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("followers",it.response().toString())
                                Log.d("followers",it.response().body().toString())
                                Log.d("followers",it.response().body().toString())
                                Log.d("followers",it.response().errorBody().toString())
                                Log.d("followers",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("followers",it.toString())
                            }
                        }
                )
    }
}