package app.nileza.droidmemo.ui.post

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import app.nileza.droidmemo.R
import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.mvp.BaseMvpActivity
import app.nileza.droidmemo.utils.Utility
import kotlinx.android.synthetic.main.activity_create_note.*
import siclo.com.ezphotopicker.api.EZPhotoPick
import siclo.com.ezphotopicker.api.EZPhotoPickStorage
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig
import siclo.com.ezphotopicker.api.models.PhotoSource
import java.io.IOException


/**
 * Created by Nougat on 5/14/2017.
 */

class CreatePostActivity : BaseMvpActivity<CreatePostInterface.View, CreatePostPresenter>(), CreatePostInterface.View {

    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override var mPresenter: CreatePostPresenter = CreatePostPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        setupInstance()
        setupBottomSheet()
        setToolBar()
        setHomeButtonActive(true)
    }

    override fun initInstance() {


    }

    private fun setupInstance() {
        mPresenter.setupFirebase()

        btn_add_image.setOnClickListener {
            showBottomSheet()
        }
    }

    @SuppressLint("InflateParams")
    private fun setupBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(this@CreatePostActivity)
        bottomSheetDialog.apply {
            setContentView(bottomSheetView)
        }
    }

    private fun showBottomSheet() {
        bottomSheetView.findViewById<TextView>(R.id.menu_bottom_sheet_camera).setOnClickListener {
            EZPhotoPick.startPhotoPickActivity(this, chooseCamera())
            bottomSheetDialog.cancel()

        }

        bottomSheetView.findViewById<TextView>(R.id.menu_bottom_sheet_gallery).setOnClickListener {
            EZPhotoPick.startPhotoPickActivity(this, chooseImage())
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()
    }

    override fun onChoosePhotoFailure(txt: String) {


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_send) {

            showProgressDialog()
            val message = postMessage()

            if (!mPresenter.photoPath.isNullOrEmpty()) {
                mPresenter.uploadFromFile(mPresenter.photoPath!!)
            } else {

                mPresenter.pushMessage(message)
                finish()

            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun upLoadImageSuccess(txt: String) {

        Toast.makeText(this, "Success : $txt", Toast.LENGTH_LONG).show()
        hideProgressDialog()
        finish()

    }

    override fun upLoadImageUnSuccess(txt: String) {
        Toast.makeText(this, "Success : $txt", Toast.LENGTH_LONG).show()
        hideProgressDialog()
    }

    private fun chooseImage() = EZPhotoPickConfig().apply {
        photoSource = PhotoSource.GALLERY
        exportingSize = 900
        exportedPhotoName = "IMG_" + System.currentTimeMillis().toString()

    }

    private fun chooseCamera() = EZPhotoPickConfig().apply {

        photoSource = PhotoSource.CAMERA
        exportingSize = 900
        exportedPhotoName = "IMG_" + System.currentTimeMillis().toString()

    }

    private fun postMessage(): PostMessage {
        val message = PostMessage()
        message.apply {
            title = edt_title.text.toString()
            body = edt_post.text.toString()
            date = Utility.getTimeStamp()
        }
        mPresenter.setPostMessage(message)

        return message
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE ||
                requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {

                val pickedPhoto: Bitmap = EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap()

                mPresenter.mPickedPhoto = pickedPhoto
                setPhotoOnImageView(pickedPhoto)

                mPresenter.photoName = data?.getStringExtra(EZPhotoPick.PICKED_PHOTO_NAME_KEY)!!
                mPresenter.photoPath = EZPhotoPickStorage(this).getAbsolutePathOfStoredPhoto("", mPresenter.photoName)
                Log.d("testData", "Test ${mPresenter.photoName}")
            } catch (e: IOException) {
                e.printStackTrace()
                onChoosePhotoFailure(e.toString())
            }
        }
    }

    override fun onShowProgressDialog() = showProgressDialog()

    override fun onHideProgressDialog() = hideProgressDialog()

    private fun setPhotoOnImageView(pickedPhoto: Bitmap) = btn_add_image.setImageBitmap(pickedPhoto)
}

