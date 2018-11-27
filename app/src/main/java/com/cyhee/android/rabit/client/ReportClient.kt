package com.cyhee.android.rabit.client

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.model.ContentType
import com.cyhee.android.rabit.model.ReportFactory
import com.cyhee.android.rabit.model.ReportType
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ReportClient {

    private val TAG = ReportClient::class.qualifiedName
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    fun report(type: ContentType, id: Long, reportType: ReportType, scopeProvider: AndroidLifecycleScopeProvider,
               onSuccess: () -> Unit, onFail: (Throwable) -> Unit = {Log.d(TAG, "report fail")}) {
        val reportBody = ReportFactory.Post(reportType, type, id)
        restClient.postReport(reportBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (onSuccess, onFail)
    }
}