package com.cyhee.android.rabit.activity.notice

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class NoticePresenter(private val view: NoticeActivity) : NoticeContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun notice(id: Long) {
        restClient.notice(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            view.showNotice(it)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("notice",it.response().toString())
                                Log.d("notice",it.response().body().toString())
                                Log.d("notice",it.response().body().toString())
                                Log.d("notice",it.response().errorBody().toString())
                                Log.d("notice",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("notice",it.toString())
                            }
                        }
                )
    }

}