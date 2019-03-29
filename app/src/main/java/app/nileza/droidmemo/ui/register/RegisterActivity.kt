package app.nileza.droidmemo.ui.register

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import app.nileza.droidmemo.R
import app.nileza.droidmemo.mvp.BaseMvpActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import siclo.com.ezphotopicker.api.EZPhotoPick
import siclo.com.ezphotopicker.api.EZPhotoPickStorage
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig
import siclo.com.ezphotopicker.api.models.PhotoSource
import java.io.IOException


class RegisterActivity : BaseMvpActivity<RegisterInterface.View, RegisterPresenter>(), RegisterInterface.View {

    private var userName: String? = null
    private var passWord: String? = null
    private var confirm: String? = null
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override var mPresenter: RegisterPresenter = RegisterPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setToolBar()
        setHomeButtonActive(true)
        setupBottomSheet()
        initInstance()
        mPresenter.setupFirebase()

    }

    private fun setupBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(this@RegisterActivity)

        bottomSheetDialog.apply {

            bottomSheetDialog.setContentView(bottomSheetView)
        }

    }

    override fun initInstance() {
        btn_email_register.setOnClickListener {
            setupUserInfo()
        }

        img_profile.setOnClickListener {

            showBottomSheet()
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

    private fun setupUserInfo() {
        userName = edtUsername.text.toString()
        passWord = edtPassword.text.toString()
        confirm = edtConfirmPassword.text.toString()
        mPresenter.validateFieldUserNamePassword(userName, passWord, confirm)
    }

    private fun signInByEmail(email: String, password: String) {

        showProgressDialog()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (!task.isSuccessful) {
                        toastManager(task.exception?.message)

                    } else {

                        if (!mPresenter.photoPath.isNullOrEmpty()) {
                            mPresenter.uploadFromFile(mPresenter.photoPath!!)

                        } else {
                            mPresenter.createAccountToRealTimeDB()
                            finish()
                        }


                    }
                    hideProgressDialog()
                }
    }

    override fun onValidateFieldPass() {

        val isMatch = mPresenter.validateConfirmPassword(passWord, confirm)
        if (isMatch) {

            signInByEmail(userName!!, passWord!!)

        } else {
            toastManager("Password not match")
        }
    }

    override fun createAccountSuccess() {
        Toast.makeText(applicationContext, "Create Success", Toast.LENGTH_LONG).show()
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

    override fun upLoadImageUnSuccess(message: String) {
        toastManager("UnSuccess : $message")
        hideProgressDialog()
    }

    override fun upLoadImageSuccess(txt: String) {
        toastManager("Success : $txt")
        hideProgressDialog()
        finish()
    }

    override fun onShowProgressDialog() = showProgressDialog()

    override fun onHideProgressDialog() = hideProgressDialog()

    override fun showMessage(message: String) = toastManager(message)

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
                // onChoosePhotoFailure(e.toString())
            }
        }
    }

    private fun setPhotoOnImageView(pickedPhoto: Bitmap) = img_profile.setImageBitmap(pickedPhoto)

}
