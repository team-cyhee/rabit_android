package com.cyhee.android.rabit.activity.sign.find

import android.util.Log
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class FindPasswordPresenter(private val view: FindPasswordActivity): FindPasswordContract.Presenter {

    private val TAG = FindPasswordPresenter::class.qualifiedName
    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)

    override fun find(username: String) {
        restClient.findPassword(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            Log.d(TAG, "find password mail sent")
                            view.success()
                        },
                        {
                            Log.d(TAG, "failed to find password")
                            if(it is HttpException) {
                                when(it.response().code()) {
                                    404 -> DialogHandler.confirmDialog(view.getString(R.string.error_no_user_or_email),view)
                                    else -> DialogHandler.errorDialog(it, view)
                                }
                            }
                            else {
                                DialogHandler.errorDialog(it, view)
                            }
                        }
                )
    }
}