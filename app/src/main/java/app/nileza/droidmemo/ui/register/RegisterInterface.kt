package app.nileza.droidmemo.ui.register

import android.graphics.Bitmap
import app.nileza.droidmemo.mvp.BaseMvpPresenter
import app.nileza.droidmemo.mvp.BaseMvpView

class RegisterInterface {

    interface View : BaseMvpView {
        fun createAccountSuccess()

        fun onValidateFieldPass()
        fun upLoadImageSuccess(txt: String)
        fun upLoadImageUnSuccess(message: String)
    }
        interface Presenter : BaseMvpPresenter<View> {

            var mPickedPhoto: Bitmap?
            var photoName: String?
            var photoPath: String?

            fun setupFirebase()

            fun createAccountToRealTimeDB()

            fun validateConfirmPassword(password: String?, confirm: String?): Boolean

            fun validateFieldUserNamePassword(userName: String?, password: String?, confirm: String?)

            fun uploadFromFile(photoPath: String)
        }
    }