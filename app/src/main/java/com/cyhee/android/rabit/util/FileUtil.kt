package com.cyhee.android.rabit.util

import android.net.Uri
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import java.io.File


object FileClient {

    private val TAG = FileClient::class.qualifiedName
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    fun uploadFile(fileUri: Uri, view: AppCompatActivity, scopeProvider: AndroidLifecycleScopeProvider, callback: (String) -> Unit) {
        // use the FileUtils to get the actual file by uri
        val file = File(fileUri.path)

        // create RequestBody instance from file
        val requestFile = RequestBody.create(
                MediaType.parse("image/jpeg"),
                file
        )

        // MultipartBody.Part is used to send also the actual file name
        val body = MultipartBody.Part.createFormData("picture", file.name, requestFile)

        // add another part within the multipart request
        val descriptionString = "hello, this is description speaking"
        val description = RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString)

        restClient.fileUpload(description, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            if(it.isSuccessful) {
                                Log.d(TAG, "successfully uploaded file")
                                callback(it.headers().get("Location")!!)
                            }
                            else {
                                Log.d(TAG, "fail to upload file")
                            }
                        },
                        {
                            Log.d(TAG, "fail to upload file")
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }
}