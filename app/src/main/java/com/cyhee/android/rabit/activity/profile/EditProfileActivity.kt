package com.cyhee.android.rabit.activity.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.base.DatePickerFragment
import com.cyhee.android.rabit.model.*
import java.text.SimpleDateFormat
import java.util.*
import com.cyhee.android.rabit.activity.base.BaseLoadPictureActivity
import com.cyhee.android.rabit.api.resource.RabitUrl
import kotlinx.android.synthetic.main.activity_editprofile.*


class EditProfileActivity: BaseLoadPictureActivity(), EditProfileContract.View {
    private val TAG = EditProfileActivity::class.qualifiedName

    override var presenter : EditProfileContract.Presenter = EditProfilePresenter(this)
    private val baseUrl = "${RabitUrl.resourceUrl()}/rest/v1/files"

    private val user = App.prefs.user
    private val formatter = SimpleDateFormat("yyyy-M-d")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        presenter.userInfo()

        edit_age.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.onSet = { year, month, day ->
                edit_age.text = "$year-${month+1}-$day"
            }
            newFragment.show(supportFragmentManager, "datePicker")
        }

        gallery_btn.setOnClickListener {
            validatePermissions{getAlbum()}
        }

        camera_btn.setOnClickListener {
            validatePermissions{captureCamera()}
        }

        post_edit_info_btn.setOnClickListener{
            val name = edit_name.text.toString()

            val age: Date? = edit_age.text.toString().let { date ->
                if (date.isNotBlank())
                    formatter.parse(date)
                else null
            }

            val introduction = edit_introduction.text.toString()

            val user = UserFactory.Edit(name, age, introduction)

            if (mCurrentPhotoPath != null) {
                presenter.upload(user, Uri.parse(mCurrentPhotoPath))
            } else {
                presenter.editUser(user)
            }
        }
    }

    override fun showUserInfo(user: User) {
        edit_name.setText(user.name)
        if (user.birth != null) {
            edit_age.text = SimpleDateFormat("yyyy-M-d").format(user.birth)
        }
        edit_introduction.setText(user.introduction)

        if (upload_image != null && user.files.isNotEmpty()) {
            Log.d(TAG, "$baseUrl/${user.files.first().id}")
            Glide.with(this).load("$baseUrl/${user.files.first().id}").into(upload_image)
        }
    }

}