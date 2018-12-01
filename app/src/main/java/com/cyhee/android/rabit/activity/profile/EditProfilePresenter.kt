package com.cyhee.android.rabit.activity.profile

import android.net.Uri
import android.util.Log
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PutClient
import com.cyhee.android.rabit.model.UserFactory
import com.cyhee.android.rabit.util.FileClient
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class EditProfilePresenter(private val view: EditProfileActivity) : EditProfileContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)
    private val user = App.prefs.user

    override fun userInfo() {
        restClient.user(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            view.showUserInfo(it)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("user",it.response().toString())
                                Log.d("user",it.response().body().toString())
                                Log.d("user",it.response().body().toString())
                                Log.d("user",it.response().errorBody().toString())
                                Log.d("user",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("user",it.toString())
                            }
                        }
                )
    }

    override fun upload(userInfo: UserFactory.Edit, fileUri: Uri?) {
        if (fileUri != null)
            FileClient.uploadFile(fileUri, view, scopeProvider) { path ->
                userInfo.fileId = path.split("/").last().toLongOrNull()
                PutClient.putUser(user, userInfo, scopeProvider) {
                    view.finish()
                }
            }
        else
            PutClient.putUser(user, userInfo, scopeProvider) {
                view.finish()
            }
    }

    override fun editUser(userInfo: UserFactory.Edit) {
        PutClient.putUser(user, userInfo, scopeProvider) {
            view.finish()
        }
    }
}