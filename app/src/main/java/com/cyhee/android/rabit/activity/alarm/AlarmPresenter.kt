package com.cyhee.android.rabit.activity.alarm

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.model.QuestionFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class AlarmPresenter(private val view: AlarmActivity) : AlarmContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun alarms() {
        restClient.alarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            view.showAlarms(it!!.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("titles",it.response().toString())
                                Log.d("titles",it.response().body().toString())
                                Log.d("titles",it.response().body().toString())
                                Log.d("titles",it.response().errorBody().toString())
                                Log.d("titles",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("titles",it.toString())
                            }
                        }
                )
    }

}