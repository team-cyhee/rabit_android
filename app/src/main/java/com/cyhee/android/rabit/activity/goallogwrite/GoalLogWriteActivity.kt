package com.cyhee.android.rabit.activity.goallogwrite

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_goallogwrite.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import java.io.File
import java.io.IOException
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.internal.schedulers.IoScheduler
import android.support.v4.app.ActivityCompat
import android.content.DialogInterface
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.cyhee.android.rabit.activity.main.MainActivity




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
            validatePermissions{getAlbum()}
        }

        goalLogCameraBtn.setOnClickListener {
            validatePermissions{captureCamera()}
        }

        realGoalLogPostBtn.setOnClickListener{
            val content = goalLogContentText.text.toString()
            val goalLog = GoalLogFactory.Post(content)

            val parentId =
                    if (goalId != -1L) {
                        goalId
                    } else {
                        val selectedGoal = goalsNameList.selectedItem as Goal
                        selectedGoal.id
                    }

            presenter.upload(parentId, goalLog, Uri.parse(mCurrentPhotoPath))
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
            goalsNameList.adapter = spinnerAdapter
            goalsNameList.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goalsNameList.adapter = spinnerAdapter
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
                uploadImage.setImageURI(albumURI)
            }
        }
    }
}