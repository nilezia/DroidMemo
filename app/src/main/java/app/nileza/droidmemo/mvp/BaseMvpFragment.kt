package app.nileza.droidmemo.mvp

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseMvpFragment<in V : BaseMvpView, T : BaseMvpPresenter<V>> : Fragment(), BaseMvpView {

    private var mProgressDialog: ProgressDialog? = null
    protected abstract var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
        if (savedInstanceState == null) {
        } else {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    override fun onShowProgressDialog() {


    }

    override fun onHideProgressDialog() {

    }

    override fun showError(error: String?) {

    }

    override fun showError(stringResId: Int) {

    }

    override fun showMessage(srtResId: Int) {

    }

    override fun showMessage(message: String) {

    }

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(context)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setMessage("Loading...")
            mProgressDialog!!.isIndeterminate = true
        }

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideProgressDialog()
    }

    abstract fun onRestoreInstanceState(savedInstanceState: Bundle)

    abstract fun bindView(view: View?)
}
