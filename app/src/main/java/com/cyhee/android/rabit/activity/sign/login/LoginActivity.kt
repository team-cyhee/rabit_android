package com.cyhee.android.rabit.activity.sign.login

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.sign.find.FindPasswordActivity
import com.cyhee.android.rabit.activity.sign.register.RegisterActivity
import com.cyhee.android.rabit.api.response.TokenData
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_login.*
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.auth.api.Auth



/**
 * 로그인 화면을 담당하는 activity
 */
class LoginActivity : AppCompatActivity(), LoginContract.View, GoogleApiClient.OnConnectionFailedListener  {

    private val TAG = LoginActivity::class.qualifiedName
    private val RC_SIGN_IN = 1000
    private val RC_REGISTER = 2000
    override var presenter : LoginContract.Presenter = LoginPresenter(this)

    private var mGoogleApiClient: GoogleApiClient? = null
    private val WEB_CLIENT_ID = "911047158248-u72ju0uvg1l95fiehhpdund7vf182as9.apps.googleusercontent.com"
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            val username = username_text.text.toString()
            val password = password_text.text.toString()

            presenter.login(username, password)
        }

        register_btn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivityForResult(intent, RC_REGISTER)
        }

        find_password_btn.setOnClickListener {
            val intent = Intent(this@LoginActivity, FindPasswordActivity::class.java)
            startActivity(intent)
        }

        setFacebook()
        setGoogle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "requestCode = $requestCode, resultCode = $resultCode")
        onActivityResultFacebook(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultGoogle(requestCode, resultCode, data)

        when(requestCode) {
            RC_REGISTER -> {
                data?.apply {
                    when(resultCode) {
                        RegisterActivity.RESULT_BASIC -> {
                            presenter.login(getStringExtra("username"), getStringExtra("password"))
                        }
                        RegisterActivity.RESULT_BEARER -> {
                            val token = data.getStringExtra("token")
                            when(getStringExtra("social")) {
                                "facebook" -> presenter.loginByFacebook(token)
                                "google" -> presenter.loginByGoogle(token)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed")
        DialogHandler.confirmDialog("Connection failed", this)
    }

    override fun success(tokenData : TokenData) {
        Log.d(TAG,"login success!")
        App.prefs.token = tokenData.accessToken
        App.prefs.refreshToken = tokenData.refreshToken
        App.prefs.user = tokenData.user

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun fail (t: Throwable?) {
        Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_SHORT).show()
        Log.d(TAG,"login fail!")
    }

    fun socialRegister(type: String, token: String) {
        MaterialDialog.Builder(this)
                .content(getString(R.string.social_register,"${type.capitalize()}"))
                .positiveText(R.string.confirm)
                .negativeText(R.string.reset)
                .onPositive { _, _ ->
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    intent.putExtra("type", type)
                    intent.putExtra("token", token)
                    startActivityForResult(intent, RC_REGISTER)
                }
                .show()
    }

    private fun onActivityResultFacebook(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun onActivityResultGoogle(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.d(TAG, result.isSuccess.toString())
            if (result.isSuccess) {
                Log.d(TAG,"success on google login")
                val account = result.signInAccount
                val token: String = account?.idToken!!
                Log.d(TAG, "trying login with $token")
                presenter.loginByGoogle(token)
            }
            else{
                Log.d(TAG,"fail on google login")
            }
        }
    }

    private fun setFacebook() {
        facebook_login_btn.setReadPermissions("email")
        facebook_login_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "success to get answer from facebook login request")
                presenter.loginByFacebook(loginResult.accessToken.token)
                LoginManager.getInstance().logOut()
            }

            override fun onCancel() {
                DialogHandler.confirmDialog(getString(R.string.canceled), this@LoginActivity)
            }

            override fun onError(exception: FacebookException) {
                DialogHandler.confirmDialog(getString(R.string.unknown_error_message), this@LoginActivity)
            }
        })
    }

    private fun setGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build()

        google_login_btn.setOnClickListener{
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }
}