package com.cyhee.android.rabit.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.response.TokenData
import com.cyhee.android.rabit.api.service.AuthApi
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 로그인 화면을 담당하는 activity
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            requestToken()
        }
    }

    private fun requestToken() {
        val username = emailText.text.toString()
        val password = passwordText.text.toString()

        val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)
        val tokenRequest = restClient.token(username, password)

        tokenRequest.enqueue(object : Callback<TokenData> {
            /**
             * 인증 성공 token 획득
             */
            override fun onResponse(call: Call<TokenData>?, response: Response<TokenData>?) {
                Toast.makeText(this@LoginActivity, response?.body().toString(), Toast.LENGTH_SHORT).show()
                Log.d("loginRequest","login success!")
                App.prefs.token = response?.body()!!.accessToken
            }

            // TODO: 인증 실패
            override fun onFailure(call: Call<TokenData>?, t: Throwable?) {
                Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("loginRequest","login fail!")
            }
        })
    }
}