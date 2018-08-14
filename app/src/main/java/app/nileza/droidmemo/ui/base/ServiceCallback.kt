package app.nileza.droidmemo.ui.base

/**
 * Created by NileZia on 29/12/2017 AD.
 */
interface ServiceCallback<in T> {
    fun onSuccess(response: T)
    fun onFailed(msg: String?)


}
