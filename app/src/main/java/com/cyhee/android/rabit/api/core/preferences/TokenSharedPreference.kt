package com.cyhee.android.rabit.api.core.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class TokenSharedPreference(context: Context) {
    private val PREFS_FILENAME = "com.cyhee.raebit.prefs"
    private val TOKEN = "token"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Activity.MODE_PRIVATE)

    // In SharedPreferences
    // apply -> async (better), commit -> sync
    var token: String
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()
}