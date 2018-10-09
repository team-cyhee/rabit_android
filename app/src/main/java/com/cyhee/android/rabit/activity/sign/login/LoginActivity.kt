package com.cyhee.android.rabit.activity.sign.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.goal.GoalListActivity
import com.cyhee.android.rabit.api.response.TokenData
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 로그인 화면을 담당하는 activity
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    override var presenter : LoginContract.Presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            val username = emailText.text.toString()
            val password = passwordText.text.toString()

            presenter.login(username, password)
        }

        registerBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun success(tokenData : TokenData) {
        Toast.makeText(this@LoginActivity, tokenData.toString(), Toast.LENGTH_SHORT).show()
        Log.d("loginRequest","login success!")
        App.prefs.token = tokenData.accessToken

        val intent = Intent(this@LoginActivity, GoalListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun fail (t: Throwable?) {
        Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_SHORT).show()
        Log.d("loginRequest","login fail!")
    }
}