package app.nileza.droidmemo.ui.login

import app.nileza.droidmemo.mvp.BaseMvpPresenter
import app.nileza.droidmemo.mvp.BaseMvpView

class LoginInterface {


    interface View : BaseMvpView

    interface Presenter : BaseMvpPresenter<View>

}