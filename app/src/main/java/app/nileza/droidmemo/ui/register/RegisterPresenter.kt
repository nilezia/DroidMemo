package app.nileza.droidmemo.ui.register

import android.graphics.Bitmap
import android.net.Uri
import app.nileza.droidmemo.data.User
import app.nileza.droidmemo.mvp.BaseMvpPresenterImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class RegisterPresenter : BaseMvpPresenterImpl<RegisterInterface.View>(), RegisterInterface.Presenter {


    override var mPickedPhoto: Bitmap? = null
    override var photoName: String? = null
    override var photoPath: String? = null

    private var folderRef: StorageReference? = null
    private var mUserDatabase: DatabaseReference? = null
    private var mDatabase: DatabaseReference? = null
    private var mStorageRef: StorageReference? = null
    private var mUser: FirebaseUser? = null

    private var linkPath: String? = null

    override fun setupFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference

        mStorageRef = FirebaseStorage.getInstance().reference
        mUserDatabase = mDatabase?.child("user")
        folderRef = mStorageRef?.child("profile")
    }

    override fun createAccountToRealTimeDB() {

        mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {

            val myUser = User(mUser?.email, mUser?.email, linkPath)
            mUserDatabase?.child(mUser?.uid)?.setValue(myUser)

        }
    }

    override fun validateConfirmPassword(password: String?, confirm: String?): Boolean {

        var isMatch = false

        if (!(password.isNullOrEmpty() && confirm.isNullOrEmpty())) {

            if (password.equals(confirm)) {

                isMatch = true
            }

        }
        return isMatch
    }

    override fun validateFieldUserNamePassword(userName: String?, password: String?, confirm: String?) {

        when {
            userName.isNullOrEmpty() -> {
                mView?.showMessage("UserName is empty")

            }
            password.isNullOrEmpty() -> {
                mView?.showMessage("Password is empty")

            }
            confirm.isNullOrEmpty() -> {
                mView?.showMessage("Confirm is empty")

            }
            else -> {

                mView?.onValidateFieldPass()

            }
        }
    }

    override fun uploadFromFile(photoPath: String) {

        val file = Uri.fromFile(File(photoPath))
        val imageRef = folderRef?.child(file.lastPathSegment)
        val mUploadTask = imageRef?.putFile(file)

        mUploadTask?.addOnFailureListener({
            mView?.upLoadImageUnSuccess(it.message!!)

        })?.addOnSuccessListener({
            mView?.onHideProgressDialog()
            linkPath = it.downloadUrl.toString()
            createAccountToRealTimeDB()
            mView?.upLoadImageSuccess(it.uploadSessionUri.toString())


        })
    }
}


