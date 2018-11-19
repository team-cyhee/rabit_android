package com.cyhee.android.rabit.activity.sign.find

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.base.DialogHandler
import kotlinx.android.synthetic.main.activity_login.*

class FindPasswordActivity : AppCompatActivity(), FindPasswordContract.View {

    private val TAG = FindPasswordActivity::class.qualifiedName
    override var presenter : FindPasswordContract.Presenter = FindPasswordPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        findPasswordBtn.setOnClickListener {
            presenter.find(usernameText.text.toString())
        }
    }

    override fun success() {
        DialogHandler.confirmDialog(getString(R.string.find_password_confirm), this)
        finish()
    }
}