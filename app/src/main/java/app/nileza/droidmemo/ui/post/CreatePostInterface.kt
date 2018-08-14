package app.nileza.droidmemo.ui.post

import android.graphics.Bitmap
import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.mvp.BaseMvpPresenter
import app.nileza.droidmemo.mvp.BaseMvpView


/**
 * Created by NileZia on 7/3/2017 AD.
 */

class CreatePostInterface {

    interface View : BaseMvpView {

        fun upLoadImageSuccess(txt: String)
        fun upLoadImageUnSuccess(txt: String)
        fun onChoosePhotoFailure(txt: String)
    }

    interface Presenter : BaseMvpPresenter<View> {

        var mPickedPhoto: Bitmap?
        var photoName: String?
        var photoPath: String?

        fun setupFirebase()

        fun pushMessage(post: PostMessage?)

        fun uploadFromFile(path: String)

        fun setPostMessage(postMessage: PostMessage?)

        fun getPostMessage(): PostMessage?
    }
}
