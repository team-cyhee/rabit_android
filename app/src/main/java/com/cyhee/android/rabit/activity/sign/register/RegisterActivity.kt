package com.cyhee.android.rabit.activity.sign.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.UserFactory
import com.cyhee.android.rabit.activity.sign.register.validator.EmailValidator
import com.cyhee.android.rabit.activity.sign.register.validator.PasswordValidator
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

/**
 * 로그인 화면을 담당하는 activity
 */
class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    override var presenter : RegisterContract.Presenter = RegisterPresenter(this)
    private val passwordValidator = PasswordValidator()
    private val disposables by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var validAll = false

        var passwordValid: Observable<Boolean> = password_text
                .textChanges()
                .map {
                    passwordValidator.valid(password_text)
                }

        var passwordCheck: Observable<Boolean> = Observable
                .merge(password_text.textChanges(), password_check_text.textChanges())
                .map {
                    if(password_check_text.text.toString() != password_text.text.toString()) {
                        password_check_text.error = "패스워드와 패스워드 체크가 다릅니다."
                        false
                    }
                    else {
                        true
                    }
                }

        var emailValid: Observable<Boolean> = email_text
                .textChanges()
                .map {
                    if(!EmailValidator.valid(it)) {
                        email_text.error = "올바른 이메일 형식이 아닙니다."
                        false
                    }
                    true
                }


        Observables.combineLatest(passwordValid, passwordCheck, emailValid) {
            valid1, valid2, valid3 -> valid1 && valid2 && valid3
        }.subscribe {
            valid -> validAll = valid
        }.apply {
            disposables.add(this)
        }

        register_btn.setOnClickListener {
            val username = username_text.text.toString()
            val password = password_text.text.toString()
            val email = email_text.text.toString()
            val phone = phone_text.text.toString()
            val age = age_text.text.toString()

            val user = UserFactory.Post(username, email, password, phone, Date())

            if(validAll)
                presenter.register(user)
            else
                Toast.makeText(this@RegisterActivity, "입력이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun success() {
        Log.d("register request", "registered")
        finish()
    }

    override fun duplicatedUsername() {
        username_text.error = "중복된 username 입니다."
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}