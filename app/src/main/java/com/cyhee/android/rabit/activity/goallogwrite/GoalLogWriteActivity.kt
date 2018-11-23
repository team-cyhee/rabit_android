package com.cyhee.android.rabit.activity.goallogwrite

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goallogwrite.*
import java.io.File
import java.io.IOException
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import android.widget.ArrayAdapter
import android.widget.Toast
import com.uber.autodispose.autoDisposable


class GoalLogWriteActivity: AppCompatActivity(), GoalLogWriteContract.View {

    private val TAG = GoalLogWriteActivity::class.qualifiedName
    override var presenter : GoalLogWriteContract.Presenter = GoalLogWritePresenter(this)

    private val user = App.prefs.user
    private val authorites = "com.cyhee.android.rabit.provider"

    private val REQUEST_TAKE_PHOTO = 2222
    private val REQUEST_TAKE_ALBUM = 3333
    private val REQUEST_IMAGE_CROP = 4444

    private var imageUri: Uri? = null
    private var photoURI: Uri? = null
    private var albumURI:Uri? = null

    private var mCurrentPhotoPath: String? = null


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

    /*private fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(columnIndex)
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }*/

    private fun validatePermissions(selectPhoto:() -> Unit) {
        val rxPermission = RxPermissions(this)
        if (rxPermission.isGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            selectPhoto()
        } else {
            rxPermission
                    .request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                    .subscribe { status ->
                        if (status) {
                            selectPhoto()
                        }
                    }
        }
    }

    override fun showGoalNames(goals: MutableList<Goal>?) {
        if (goals == null) {
            //TODO: 작동안함
            val noGoal: Array<String> = arrayOf("새로운 토끼를 잡아보세요")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, noGoal)
            goals_name_list.adapter = spinnerAdapter
            goals_name_list.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goals_name_list.adapter = spinnerAdapter
        }
    }

    private fun captureCamera() {
        val state = Environment.getExternalStorageState()
        // 외장 메모리 검사
        if (Environment.MEDIA_MOUNTED == state) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    Log.e("captureCamera Error", ex.toString())
                }

                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함

                    val providerURI = FileProvider.getUriForFile(this, authorites, photoFile)
                    imageUri = providerURI

                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show()
            return
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val imageFileName ="${System.currentTimeMillis()}.jpg"
        var imageFile: File? = null
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/Pictures", "rabit")

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString())
            storageDir.mkdirs()
        }

        imageFile = File(storageDir, imageFileName)
        mCurrentPhotoPath = imageFile.absolutePath

        return imageFile
    }


    private fun getAlbum() {
        Log.i("getAlbum", "Call")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_TAKE_ALBUM)
    }

    private fun galleryAddPic(): Uri {
        Log.i("galleryAddPic", "Call")
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        val f = File(mCurrentPhotoPath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show()
        return contentUri
    }

    // 카메라 전용 크랍
    private fun cropImage() {
        Log.i("cropImage", "Call")
        Log.i("cropImage", "photoURI : $photoURI / albumURI : $albumURI")

        val cropIntent = Intent("com.android.camera.action.CROP")

        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        cropIntent.setDataAndType(photoURI, "image/*")
        cropIntent.putExtra("outputX", 200) // crop한 이미지의 x축 크기, 결과물의 크기
        cropIntent.putExtra("outputY", 200) // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1) // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1) // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true)
        cropIntent.putExtra("output", albumURI) // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                try {
                    Log.i("REQUEST_TAKE_PHOTO", "OK")
                    photoURI = galleryAddPic()
                    albumURI = photoURI
                    cropImage()
                } catch (e: Exception) {
                    Log.e("REQUEST_TAKE_PHOTO", e.toString())
                }

            } else {
                Toast.makeText(this@GoalLogWriteActivity, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show()
            }

            REQUEST_TAKE_ALBUM -> if (resultCode == Activity.RESULT_OK) {

                if (data!!.data != null) {
                    try {
                        var albumFile = createImageFile()
                        photoURI = data.data
                        albumURI = Uri.fromFile(albumFile)
                        cropImage()
                    } catch (e: Exception) {
                        Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString())
                    }

                }
            }

            REQUEST_IMAGE_CROP -> if (resultCode == Activity.RESULT_OK) {

                galleryAddPic()
                upload_image.setImageURI(albumURI)
            }
        }
    }
}