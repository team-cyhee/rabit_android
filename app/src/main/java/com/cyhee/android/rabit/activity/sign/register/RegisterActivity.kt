package com.cyhee.android.rabit.activity.sign.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.sign.login.LoginActivity
import com.cyhee.android.rabit.model.UserFactory
import com.cyhee.android.rabit.activity.sign.register.validator.EmailValidator
import com.cyhee.android.rabit.activity.sign.register.validator.PasswordValidator
import com.cyhee.android.rabit.api.response.TokenData
import com.cyhee.android.rabit.base.DatePickerFragment
import com.jakewharton.rxbinding2.view.visibility
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.combineLatest
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * 로그인 화면을 담당하는 activity
 */
class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    companion object {
        const val RESULT_BASIC = 1000
        const val RESULT_BEARER = 1001
    }

    private val TAG = RegisterActivity::class.qualifiedName
    override var presenter: RegisterContract.Presenter = RegisterPresenter(this)
    private val passwordValidator = PasswordValidator()
    private val disposables by lazy {
        CompositeDisposable()
    }
    private var regType: String? = null
    private lateinit var token: String
    private var validAll = false
    private val formatter = SimpleDateFormat("yyyy-M-d")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        intentListener()
        initValidation()

        birth_text.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.onSet = { year, month, day ->
                birth_text.setText("$year-${month+1}-$day")
            }
            newFragment.show(supportFragmentManager, "datePicker")
        }

        register_btn.setOnClickListener {
            val username = username_text.text.toString()
            val password = password_text.text.toString()
            val email = email_text?.text.toString()
            val name = name_text?.text.toString()
            //val phone = phoneText.text.toString()
            val birth: Date? = birth_text.text.toString().let {
                if (it.isNotBlank())
                    formatter.parse(it)
                null
            }

            val user = UserFactory.Post(username, name, email, password, null, birth)

            if (validAll)
                if (regType == null) presenter.register(user)
                else presenter.socialRegister(regType as String, token, user)
            else
                Toast.makeText(this@RegisterActivity, "입력이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
        }

        login_btn.setOnClickListener {
            finish()
        }
    }

    fun resultWithBasic(username: String, password: String) {
        Log.d(TAG, "registered with rabit original")
        val intent = Intent()
        intent.putExtra("username", username)
        intent.putExtra("password", password)
        setResult(RESULT_BASIC, intent)
        finish()
    }

    fun resultWithBearer(social: String, token: String) {
        Log.d(TAG, "registered with $social")
        val intent = Intent()
        intent.putExtra("social", social)
        intent.putExtra("token", token)
        setResult(RESULT_BEARER, intent)
        finish()
    }

    override fun duplicatedUsername() {
        username_text.error = "중복된 username 입니다."
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private fun intentListener() {
        regType = intent.getStringExtra("type")?.apply {
            email_card.visibility = View.GONE
            pw_card.visibility = View.GONE
            pw2_card.visibility = View.GONE
            token = intent.getStringExtra("token")
        }

        if (regType == null) {
            email_card.visibility = View.VISIBLE
            pw_card.visibility = View.VISIBLE
            pw2_card.visibility = View.VISIBLE
        }
    }

    private fun initValidation() {

        var observables = ArrayList<Observable<Boolean>>()

        if (regType == null) {
            // password
            observables.add(
                    password_text
                            .textChanges()
                            .map {
                                passwordValidator.valid(password_text)
                            }
            )
            // compare passwords
            observables.add(
                    Observable
                            .merge(password_text.textChanges(), password_check_text.textChanges())
                            .map {
                                if (password_check_text.text.toString() != password_text.text.toString()) {
                                    password_check_text.error = "패스워드와 패스워드 체크가 다릅니다."
                                    false
                                } else {
                                    true
                                }
                            }
            )
            // email
            observables.add(email_text
                    .textChanges()
                    .map {
                        if (!EmailValidator.valid(it)) {
                            email_text.error = "올바른 이메일 형식이 아닙니다."
                            false
                        }
                        true
                    }
            )
        }
        // username
        observables.add(username_text
                .textChanges()
                .map {
                    if (it.matches("[A-Za-z\\d]{4,20}".toRegex()))
                        true
                    else {
                        username_text.error = "4-20 글자의 영소문자"
                        false
                    }
                }
        )
        // name
        observables.add(name_text
                .textChanges()
                .map {
                    if (it.length <= 30)
                        true
                    else {
                        name_text.error = "30 글자 이하"
                        false
                    }
                }
        )

        observables.combineLatest {
            it.reduce { acc, b -> acc && b }
        }.subscribe { valid ->
            validAll = valid
        }.apply {
            disposables.add(this)
        }
    }
}