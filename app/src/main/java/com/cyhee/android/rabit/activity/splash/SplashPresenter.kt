package com.cyhee.android.rabit.activity.splash

import android.util.Log
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.core.preferences.TokenSharedPreference
import com.cyhee.android.rabit.api.service.AuthApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashPresenter (private val view : SplashActivity) : SplashContract.Presenter {

    private val TAG = SplashPresenter::class.qualifiedName
    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)


    override fun verify(pref: TokenSharedPreference) {
        Log.d(TAG, "call verify func ${pref.token}")
        restClient.checkToken(pref.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d(TAG,"token is not expired")
                            view.startMainActivity()
                        },
                        {
                            Log.d(TAG, "token is not verified")
                            view.startLoginActivity()
                        }
                )
    }
}