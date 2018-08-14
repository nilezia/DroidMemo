package app.nileza.droidmemo.ui.friend.myfriend

import app.nileza.droidmemo.mvp.BaseMvpPresenter
import app.nileza.droidmemo.mvp.BaseMvpView

class MyFriendInterface {

    interface View : BaseMvpView

    interface Presenter : BaseMvpPresenter<View>


}