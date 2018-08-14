package app.nileza.droidmemo.ui.feed

import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.ui.base.ServiceCallback

/**
 * Created by NileZia on 29/12/2017 AD.
 */
interface FeedContractManager {

    fun getAllPost(callback:ServiceCallback<MutableList<PostMessage>>)
}