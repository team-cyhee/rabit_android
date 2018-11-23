package com.cyhee.android.rabit.activity.alarm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.Alarm
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class AlarmActivity: AppCompatActivity(), AlarmContract.View {
    override var presenter : AlarmContract.Presenter = AlarmPresenter(this)
    private var alarmAdapter: AlarmViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        search_btn.setOnClickListener(IntentListener.toSearchListener())

        presenter.alarms()
    }

    override fun showAlarms(alarms: MutableList<Alarm>) {
        if (alarmAdapter == null) {
            alarmAdapter = AlarmViewAdapter(alarms)
            alarm_list_view.adapter = alarmAdapter
        } else {
            alarmAdapter!!.appendAlarms(alarms)
        }
    }
}