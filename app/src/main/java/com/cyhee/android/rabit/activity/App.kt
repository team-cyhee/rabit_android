package com.cyhee.android.rabit.activity

import android.app.Application
import android.content.Context
import com.cyhee.android.rabit.api.core.preferences.TokenSharedPreference

class App : Application() {
    init {
        instance = this
        print("@@@@@@@!!!!!!!!!!!!!!@#@@@@@@@@@@@@")
    }

    companion object {
        lateinit var prefs : TokenSharedPreference
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        prefs = TokenSharedPreference(applicationContext)
        super.onCreate()
    }
}