package com.cyhee.android.rabit.activity.sign.find

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.cyhee.android.rabit.R
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : AppCompatActivity(), FindPasswordContract.View {

    private val TAG = FindPasswordActivity::class.qualifiedName
    override var presenter : FindPasswordContract.Presenter = FindPasswordPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        findBtn.setOnClickListener {
            presenter.find(usernameText.text.toString())
        }
    }

    override fun success() {
        MaterialDialog.Builder(this)
                .content(getString(R.string.find_password_confirm))
                .positiveText(getString(R.string.confirm))
                .onPositive { _, _ ->
                    finish()
                }
                .show()
    }
}