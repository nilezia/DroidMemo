package app.nileza.droidmemo.ui.post

import android.graphics.Bitmap
import android.net.Uri
import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.mvp.BaseMvpPresenterImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


/**
 * Created by NileZia on 7/3/2017 AD.
 */

class CreatePostPresenter : BaseMvpPresenterImpl<CreatePostInterface.View>(), CreatePostInterface.Presenter {

    private var firebaseUser: FirebaseUser? = null
    private var mDatabase: DatabaseReference? = null
    private var mPostDatabase: DatabaseReference? = null
    private var mUserPostDatabase: DatabaseReference? = null
    private var mStorageRef: StorageReference? = null
    private var folderRef: StorageReference? = null
    private var postMessage: PostMessage? = null


    override var mPickedPhoto: Bitmap? = null
    override var photoName: String? = null
    override var photoPath: String? = null

    init {
        postMessage = PostMessage()

    }

    override fun setupFirebase() {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        mDatabase = FirebaseDatabase.getInstance().reference
        mStorageRef = FirebaseStorage.getInstance().reference
        mPostDatabase = mDatabase?.child("post")
        mUserPostDatabase = mDatabase?.child("user-post")

        folderRef = mStorageRef?.child("photos")

    }

    override fun pushMessage(post: PostMessage?) {
        val mUserPost = mUserPostDatabase?.child(firebaseUser?.uid)
        post?.uuid = firebaseUser?.uid
        mUserPost?.push()?.setValue(post)
        mPostDatabase?.push()?.setValue(post)

    }

    override fun uploadFromFile(path: String) {
        val file = Uri.fromFile(File(path))
        val imageRef = folderRef?.child(file.lastPathSegment)
        val mUploadTask = imageRef?.putFile(file)

        mUploadTask?.addOnFailureListener {
            mView?.upLoadImageUnSuccess(it.message!!)

        }?.addOnSuccessListener {
            mView?.onHideProgressDialog()
            this.postMessage?.link = it.downloadUrl.toString()
            pushMessage(this.postMessage!!)
            mView?.upLoadImageSuccess(it.uploadSessionUri.toString())


        }
    }

    override fun setPostMessage(postMessage: PostMessage?) {

        this.postMessage = postMessage

    }

    override fun getPostMessage(): PostMessage? {

        return postMessage
    }
}
