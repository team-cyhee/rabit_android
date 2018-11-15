package com.cyhee.android.rabit.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build

object DrawableUtil {

    fun getDrawable (context: Context, id: Int)  =
        if(Build.VERSION.SDK_INT >= 21) context.getDrawable(id)!!
        else context.resources.getDrawable(id)!!
}