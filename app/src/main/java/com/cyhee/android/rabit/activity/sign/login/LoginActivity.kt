package com.cyhee.android.rabit.activity.sign.login

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.activity.main.MainActivity
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
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth




/**
 * 로그인 화면을 담당하는 activity
 */
class LoginActivity : AppCompatActivity(), LoginContract.View, GoogleApiClient.OnConnectionFailedListener  {

    private val TAG = LoginActivity::class.qualifiedName
    private val RC_SIGN_IN = 1000
    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private val WEB_CLIENT_ID = "392949556910-9h23r7qdrhamh4evqsd2mdndretngll2.apps.googleusercontent.com"
    private val callbackManager = CallbackManager.Factory.create()
    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
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

        setFacebook()
        setGoogle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "requestCode = $requestCode, resultCode = $resultCode")
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.d(TAG, result.isSuccess.toString())
            if (result.isSuccess) {
                Log.d(TAG,"success on google login")
                val account = result.signInAccount
                val token: String = account?.idToken!!
                presenter.loginByGoogle(token)
            }
            else{
                Log.d(TAG,"fail on google login")
            }
        }
    }

    private fun setFacebook() {
        facebookLoginBtn.setReadPermissions("email")
        facebookLoginBtn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
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
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build()

        mAuth = FirebaseAuth.getInstance()

        googleLoginBtn.setOnClickListener{
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        val user = mAuth!!.currentUser
                        Log.d(TAG, user.toString())
                    } else {
                        // Sign in fails
                        Log.d(TAG, "firebaseAuthWithGoogle():" + acct.id!!)
                    }
                }
    }

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {
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
}