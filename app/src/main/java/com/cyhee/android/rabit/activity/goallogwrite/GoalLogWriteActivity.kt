package com.cyhee.android.rabit.activity.goallogwrite

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goallogwrite.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import java.io.File
import java.io.IOException
import android.graphics.Bitmap
import android.provider.MediaStore.MediaColumns



class GoalLogWriteActivity: AppCompatActivity(), GoalLogWriteContract.View {
    override var presenter : GoalLogWriteContract.Presenter = GoalLogWritePresenter(this)

    private val user = App.prefs.user
    private val CAMERA = 0
    private val ALBUM = 1
    private val CROP = 2
    private var imgUri: Uri? = null
    private var photoUri: Uri? = null
    private var albumUri: Uri? = null

    private var mCurrentPhotoPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallogwrite)

        var goalId: Long = -1
        if (intent.hasExtra("goalId")) {
            goalId = intent.getLongExtra("goalId", -1)
            val content = intent.getStringExtra("content")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayListOf(content))
            goalsNameList.adapter = spinnerAdapter
            goalsNameList.isEnabled = false
        } else {
            presenter.goalNames()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        goalLogGalleryBtn.setOnClickListener {
            selectAlbum()
        }

        goalLogCameraBtn.setOnClickListener {
            takePhoto()
        }

        realGoalLogPostBtn.setOnClickListener{
            val content = goalLogContentText.text.toString()
            val goalLog = GoalLogFactory.Post(content)
            val img =

            if (goalId != (-1).toLong()) {
                presenter.postGoalLog(goalId, goalLog)
            } else {
                val selectedGoal = goalsNameList.selectedItem as Goal
                presenter.postGoalLog(selectedGoal.id, goalLog)
            }

        }
    }

    override fun showGoalNames(goals: MutableList<Goal>?) {
        if (goals == null) {
            //TODO: 작동안함
            val noGoal: Array<String> = arrayOf("새로운 토끼를 잡아보세요")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, noGoal)
            goalsNameList.adapter = spinnerAdapter
            goalsNameList.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goalsNameList.adapter = spinnerAdapter
        }
    }


    //앨범 선택
    private fun selectAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, ALBUM)

    }

    //사진 찍기
    private fun takePhoto() {

        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (photoFile != null) {
                    println(packageName.toString())
                    val providerURI = FileProvider.getUriForFile(this, packageName, photoFile)
                    imgUri = providerURI
                    println(imgUri)
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI)
                    startActivityForResult(intent, CAMERA)
                }
            }
        } else {
            Log.v("알림", "저장공간에 접근 불가능")
            return
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val imgFileName = System.currentTimeMillis().toString() + ".jpg"
        var imageFile: File?
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/Pictures", "rabit")

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        imageFile = File(storageDir, imgFileName)
        mCurrentPhotoPath = imageFile.absolutePath

        return imageFile
    }


    private fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(mCurrentPhotoPath)
        val contentUri = Uri.fromFile(f)

        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
    }

    private fun cropImage(uri: Uri?) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 200)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("scale", true)
        intent.putExtra("return-data", true)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CROP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            ALBUM -> {
                //앨범에서 가져오기
                if (data!!.data != null) {
                    try {
                        val albumFile: File? = createImageFile()
                        photoUri = data.data
                        albumUri = Uri.fromFile(albumFile)

                        cropImage(photoUri)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.v("알림", "앨범에서 가져오기 에러")
                    }
                }
            }

            CAMERA -> {
                //촬영
                try {
                    cropImage(imgUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            CROP -> {
                galleryAddPic()

                val extras = data!!.extras
                val imageBitmap = extras.get("data") as Bitmap
                uploadImage.setImageBitmap(imageBitmap)
            }
        }
    }
}