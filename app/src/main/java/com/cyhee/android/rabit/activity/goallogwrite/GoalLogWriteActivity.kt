package com.cyhee.android.rabit.activity.goallogwrite

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goallogwrite.*
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.cyhee.android.rabit.activity.base.BaseLoadPictureActivity


class GoalLogWriteActivity: BaseLoadPictureActivity(), GoalLogWriteContract.View {

    private val TAG = GoalLogWriteActivity::class.qualifiedName
    override var presenter : GoalLogWriteContract.Presenter = GoalLogWritePresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallogwrite)

        var goalId: Long = -1
        when {
            intent.hasExtra("goalId") -> {
                goal_name.visibility = View.VISIBLE
                goals_name_list.visibility = View.GONE
                goalId = intent.getLongExtra("goalId", -1)
                val goalContent = intent.getStringExtra("goalContent")
                goal_name.text = goalContent

                real_goal_log_post_btn.setOnClickListener{
                    val content = goal_log_content_text.text.toString()
                    val goalLog = GoalLogFactory.Post(content)

                    if (mCurrentPhotoPath != null) {
                        presenter.upload(goalId, goalLog, Uri.parse(mCurrentPhotoPath))
                    } else {
                        presenter.postGoalLog(goalId, goalLog)
                    }
                }
            }
            intent.hasExtra("goalLogId") -> {
                goal_name.visibility = View.VISIBLE
                goals_name_list.visibility = View.GONE

                val goalLogId = intent.getLongExtra("goalLogId", -1)
                val goalContent = intent.getStringExtra("goalContent")
                val content = intent.getStringExtra("content")

                goal_name.text = goalContent
                real_goal_log_post_btn.text = "수정"
                goal_log_content_text.setText(content)

                real_goal_log_post_btn.setOnClickListener {
                    val editedContent = goal_log_content_text.text.toString()
                    val goalLog = GoalLogFactory.Post(editedContent)

                    presenter.editGoalLog(goalLogId, goalLog)
                }
            }
            else -> {
                goals_name_list.visibility = View.VISIBLE
                goal_name.visibility = View.GONE
                presenter.goalNames()

                real_goal_log_post_btn.setOnClickListener{
                    val content = goal_log_content_text.text.toString()
                    val goalLog = GoalLogFactory.Post(content)
                    val selectedGoal = goals_name_list.selectedItem as Goal
                    val parentId = selectedGoal.id

                    if (mCurrentPhotoPath != null) {
                        presenter.upload(parentId, goalLog, Uri.parse(mCurrentPhotoPath))
                    } else {
                        presenter.postGoalLog(parentId, goalLog)
                    }
                }
            }
        }

        goal_log_gallery_btn.setOnClickListener {
            validatePermissions{getAlbum()}
        }

        goal_log_camera_btn.setOnClickListener {
            validatePermissions{captureCamera()}
        }
    }

    override fun showGoalNames(goals: MutableList<Goal>?) {
        if (goals == null) {
            //TODO: 작동안함
            val noGoal: Array<String> = arrayOf("새로운 토끼를 잡아보세요")
            val spinnerAdapter: SpinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, noGoal)
            goals_name_list.adapter = spinnerAdapter
            goals_name_list.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goals_name_list.adapter = spinnerAdapter
        }
    }
}