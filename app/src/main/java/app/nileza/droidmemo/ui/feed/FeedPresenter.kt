package app.nileza.droidmemo.ui.feed

import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.mvp.BaseMvpPresenterImpl
import app.nileza.droidmemo.ui.base.ServiceCallback

/**
 * Created by NileZia on 29/12/2017 AD.
 */


class FeedPresenter : BaseMvpPresenterImpl<FeedInterface.View>(), FeedInterface.Presenter {
    override fun loadFeedData() {

        val m = FeedManager()
        m.getAllPost(object : ServiceCallback<MutableList<PostMessage>> {
            override fun onSuccess(response: MutableList<PostMessage>) {
                response.sortBy {

                    it.date
                }
                mView?.onPostsUpdate(response)

            }

            override fun onFailed(msg: String?) {

            }
        })
    }
}