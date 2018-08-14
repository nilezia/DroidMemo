package app.nileza.droidmemo.mvp

import android.content.Context
import android.support.annotation.StringRes

interface BaseMvpView {

    fun onShowProgressDialog()

    fun onHideProgressDialog()

    fun getContext(): Context

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)

}
