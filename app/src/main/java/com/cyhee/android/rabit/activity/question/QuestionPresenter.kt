package com.cyhee.android.rabit.activity.question

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


class QuestionPresenter(private val view: QuestionActivity) : QuestionContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun postQuestion(question: QuestionFactory.Post) {
        restClient.postQuestion(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            DialogHandler.confirmDialog("수정이 완료되었습니다.", view) { view.finish() }
                        },
                        {
                            if(it is HttpException) {
                                Log.d("likes",it.response().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().errorBody().toString())
                                Log.d("likes",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("likes",it.toString())
                            }
                        }
                )
    }

}