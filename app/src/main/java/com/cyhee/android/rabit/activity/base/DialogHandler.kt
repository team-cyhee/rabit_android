package com.cyhee.android.rabit.activity.base

import android.content.Context
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.sign.login.LoginPresenter
import retrofit2.HttpException
import java.lang.Exception

object DialogHandler {

    private val TAG = DialogHandler::class.qualifiedName

    fun errorDialog(exception: Throwable, context: Context) {
        when (exception) {
            is HttpException -> {
                Log.d(TAG, exception.response().errorBody()?.string())
                showMessageDialog(exception.response().errorBody()!!.string(), context)
            }
            else -> {
                Log.d(TAG, exception.message)
                showMessageDialog(R.string.unknown_error_message, context)
            }
        }
    }

    fun confirmDialog(msg: String = "", context: Context) {
        showMessageDialog(msg, context)
    }

    private fun showMessageDialog(msg: String = "", context: Context) {
        MaterialDialog.Builder(context)
                .content(msg)
                .positiveText(R.string.confirm)
                .show()
    }

    private fun showMessageDialog(id: Int, context: Context) {
        MaterialDialog.Builder(context)
                .content(context.getString(id))
                .positiveText(R.string.confirm)
                .show()
    }
}