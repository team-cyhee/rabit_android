package com.cyhee.android.rabit.activity.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.goallist.GoalListActivity
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.mywall.MyWallActivity
import com.cyhee.android.rabit.activity.sign.login.LoginActivity
import com.cyhee.android.rabit.listener.IntentListener
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class SettingsActivity: AppCompatActivity(), SettingsContract.View {

    override var presenter : SettingsContract.Presenter = SettingsPresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        bottom_bar.selectTabAtPosition(3)

        search_btn.setOnClickListener(IntentListener.toSearchListener())

        /* alarm */
        alarm_btn.setOnClickListener(IntentListener.toAlarmListener())

        /* question */
        cs_btn.setOnClickListener(IntentListener.toQuestionWriteListener())

        /* notice */
        notice_btn.setOnClickListener(IntentListener.toNoticeListListener())

        /* logout */
        logout_btn.setOnClickListener{
            App.prefs.user = ""
            App.prefs.token = ""
            App.prefs.refreshToken = ""
            val intentToLogin = Intent(it.context, LoginActivity::class.java)
            finishAffinity()
            it.context.startActivity(intentToLogin)
        }

        bottom_bar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tabHome -> {
                    val intentToMain = Intent(this, MainActivity::class.java)
                    intentToMain.putExtra("username", user)
                    startActivity(intentToMain)
                }
                R.id.tabGoal -> {
                    val intentToGoalList = Intent(this, GoalListActivity:: class.java)
                    intentToGoalList.putExtra("username", user)
                    startActivity(intentToGoalList)
                }
                R.id.tabWall -> {
                    val intentToMyWall = Intent(this, MyWallActivity::class.java)
                    intentToMyWall.putExtra("username", user)
                    startActivity(intentToMyWall)
                }
            }
        }

    }

}