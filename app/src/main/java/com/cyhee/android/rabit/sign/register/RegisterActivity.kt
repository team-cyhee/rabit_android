package com.cyhee.android.rabit.sign.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.data.User
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 로그인 화면을 담당하는 activity
 */
class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    override var presenter : RegisterContract.Presenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBtn.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            val email = emailText.text.toString()
            val phone = phoneText.text.toString()
            val age = ageText.text.toString()

            val fmt = SimpleDateFormat("yyyy-MM-dd");
            val user = User(username, password, email, phone, Date())
            presenter.register(user)
        }
    }

    override fun success() {
        // TODO
        Log.d("register request", "registered")
    }
}