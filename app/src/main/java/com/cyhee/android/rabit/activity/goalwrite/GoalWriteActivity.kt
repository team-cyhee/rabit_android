package com.cyhee.android.rabit.activity.goalwrite

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.base.DatePickerFragment
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goalwrite.*
import java.text.SimpleDateFormat
import java.util.*
import com.cyhee.android.rabit.activity.base.BaseLoadPictureActivity
import com.cyhee.android.rabit.activity.base.DialogHandler
import java.lang.Exception


class GoalWriteActivity: BaseLoadPictureActivity(), GoalWriteContract.View {
    override var presenter : GoalWriteContract.Presenter = GoalWritePresenter(this)

    private val user = App.prefs.user
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("yyyy-M-d")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goalwrite)

        var parent: Long? = null
        var goalId: Long? = null


        var radio: RadioButton = none_btn
        // 함께하는 골의 경우
        if (intent.hasExtra("parent")) {
            parent = intent.getLongExtra("parent", -1)
            goal_content_text.isEnabled = false
            goal_gallery_btn.isEnabled = false
            goal_camera_btn.isEnabled = false
        }
        // 골을 수정하는 경우
        else if (intent.hasExtra("goalId")) {
            goalId = intent.getLongExtra("goalId", -1)
            real_goal_post_btn.text = "수정"
        }

        if (parent != null || goalId != null) {
            goal_content_text.setText(intent.getStringExtra("content"))

            if (intent.hasExtra("unit") && intent.hasExtra("times")) {
                val unitId = "${intent.getStringExtra("unit").toString().toLowerCase()}_btn"
                goal_radio_group.findViewById<RadioButton>(resources.getIdentifier(unitId, "id", packageName)).isChecked = true
                radio = findViewById(resources.getIdentifier(unitId, "id", packageName))

                for (i in 0 until goal_radio_group.childCount) {
                    goal_radio_group.getChildAt(i).isEnabled = false
                }
                goal_time_text.setText(intent.getIntExtra("times", -1).toString())
                goal_time_text.isEnabled = false
            }

            if (intent.hasExtra("startDate")) {
                goal_start_text.text = intent.getStringExtra("startDate")
            }
            if (intent.hasExtra("endDate")) {
                goal_end_text.text = intent.getStringExtra("endDate")
            }

            goal_content_text.setTextColor(Color.rgb(1,1,1))
        }

        goal_radio_group.setOnCheckedChangeListener { group, checkedId ->
            radio = findViewById(checkedId)
            if (radio.text == "매일") {
                goal_time_text.setText("1")
            } else {
                goal_time_text.text = null
            }
        }

        goal_start_text.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.onSet = { year, month, day ->
                goal_start_text.text = "$year-${month+1}-$day"
            }
            newFragment.show(supportFragmentManager, "datePicker")
        }

        goal_end_text.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.onSet = { year, month, day ->
                goal_end_text.text = "$year-${month+1}-$day"
            }
            newFragment.show(supportFragmentManager, "datePicker")
        }

        goal_gallery_btn.setOnClickListener {
            validatePermissions{getAlbum()}
        }

        goal_camera_btn.setOnClickListener {
            validatePermissions{captureCamera()}
        }

        real_goal_post_btn.setOnClickListener{ it ->
            val unit = when (radio.text) {
                "매일" -> GoalUnit.DAILY
                "주별" -> GoalUnit.WEEKLY
                "월별" -> GoalUnit.MONTHLY
                "년별" -> GoalUnit.YEARLY
                else -> {
                    DialogHandler.confirmDialog("목표 주기를 선택해주세요(매일/주별/월별/년별)", this)
                    return@setOnClickListener
                }
            }

            val times = try {
                goal_time_text.text.toString().toInt()
            } catch (e: Exception) {
                DialogHandler.confirmDialog("목표 횟수를 적어주세요", this)
                return@setOnClickListener
            }

            val content = goal_content_text.text.toString()

            if (content == "") {
                DialogHandler.confirmDialog("목표 이름을 정해주세요", this)
                return@setOnClickListener
            }

            val startDate: Date = goal_start_text.text.toString().let { date ->
                if (date.isNotBlank())
                    formatter.parse(date)
                else Date(System.currentTimeMillis())
            }

            if (startDate < Date(System.currentTimeMillis()) && !intent.hasExtra("goalId")) {
                DialogHandler.confirmDialog("최소 시작일은 오늘입니다", this)
                return@setOnClickListener
            }

            val endDate: Date? = goal_end_text.text.toString().let { date ->
                if (date.isNotBlank())
                    formatter.parse(date)
                else null
            }

            if (endDate != null && startDate >= endDate) {
                DialogHandler.confirmDialog("목표 종료일이 시작일보다 이전입니다", this)
                return@setOnClickListener
            }

            val goal = GoalFactory.Post(content, startDate, endDate, unit, times)

            if (mCurrentPhotoPath != null) {
                when {
                    goalId != null -> presenter.editUpload(goalId, goal, Uri.parse(mCurrentPhotoPath))
                    else -> presenter.upload(goal, Uri.parse(mCurrentPhotoPath))
                }
            } else {
                when {
                    parent != null -> presenter.postCompanion(parent, goal)
                    goalId != null -> presenter.editGoal(goalId, goal)
                    else -> presenter.postGoal(goal)
                }
            }
        }
    }
}