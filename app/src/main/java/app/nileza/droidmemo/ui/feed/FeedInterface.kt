package app.nileza.droidmemo.ui.feed

import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.mvp.BaseMvpPresenter
import app.nileza.droidmemo.mvp.BaseMvpView

/**
 * Created by NileZia on 29/12/2017 AD.
 */


interface FeedInterface {


    interface View : BaseMvpView {

        fun onPostsUpdate(posts: MutableList<PostMessage>)

        fun showEmptyView()

    }


    interface Presenter : BaseMvpPresenter<View> {

        fun loadFeedData()
    }

}