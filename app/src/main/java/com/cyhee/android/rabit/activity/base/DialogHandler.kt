package com.cyhee.android.rabit.activity.base

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.annotation.StringRes
import android.util.Log
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.cyhee.android.rabit.R
import retrofit2.HttpException

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

    fun confirmDialog(id: Int, context: Context) {
        showMessageDialog(id, context)
    }

    fun confirmDialog(msg: String = "", context: Context, callback: ()->Unit) {
        showMessageDialog(msg, context, callback)
    }

    fun checkDialog(title: String, body: String, context:Context, id: Long,  func: (Long) -> Unit) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(body)
                .setCancelable(true)
                .setPositiveButton("네"
                ) { _: DialogInterface, _: Int ->
                    func(id)
                }
                .setNegativeButton("아니오"
                ) { _: DialogInterface, _: Int ->
                }
                .create()
                .show()
    }

    private fun showMessageDialog(msg: String = "", context: Context) {
        MaterialDialog.Builder(context)
                .content(msg)
                .positiveText(R.string.confirm)
                .show()
    }

    private fun showMessageDialog(msg: String = "", context: Context, callback: ()->Unit) {
        MaterialDialog.Builder(context)
                .content(msg)
                .positiveText(R.string.confirm)
                .onPositive { _: MaterialDialog, _: DialogAction ->
                    callback()
                }
                .show()
    }

    private fun showMessageDialog(@StringRes id: Int, context: Context) {
        MaterialDialog.Builder(context)
                .content(context.getString(id))
                .positiveText(R.string.confirm)
                .show()
    }
}