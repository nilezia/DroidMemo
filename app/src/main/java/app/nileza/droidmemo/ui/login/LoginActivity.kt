package app.nileza.droidmemo.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.nileza.droidmemo.R
import app.nileza.droidmemo.mvp.BaseMvpActivity
import app.nileza.droidmemo.ui.MainActivity
import app.nileza.droidmemo.ui.register.RegisterActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseMvpActivity<LoginInterface.View, LoginPresenter>(), LoginInterface.View {
    override var mPresenter: LoginPresenter = LoginPresenter()

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    companion object {

        private const val RC_SIGN_IN = 1100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupFirebase()
        initInstance()

    }

    private fun setupFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            // hideProgressDialog();
            if (user != null) {
                // User is signed in
                startActivity(goToOtherActivity(MainActivity::class.java))
                finish()
            } else {
                setupGoogleSign()
            }
        }

    }

    override fun initInstance() {

        btn_register.setOnClickListener {
            startActivity(goToOtherActivity(RegisterActivity::class.java))
        }

        btn_sign_in_with_google.setOnClickListener {
            showProgressDialog()
            startActivityForResult(googleSignIn(), RC_SIGN_IN)

        }

        btn_email_login.setOnClickListener {

            if ((!edtUsername.text.toString().trim().isEmpty()) && (!edtPassword.text.toString().trim().isEmpty())) {
                showProgressDialog()
                signInByEmail(edtUsername.text.toString(), edtPassword.text.toString())
            } else {
                Toast.makeText(applicationContext, "UserName or Password is empty!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToOtherActivity(cls: Class<*>) = Intent(this, cls).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    private fun googleSignIn() = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)

    override fun onStart() {
        super.onStart()
        mGoogleApiClient?.connect()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()

        if (mGoogleApiClient != null && mGoogleApiClient?.isConnected!!) {
            mGoogleApiClient?.stopAutoManage(LoginActivity@ this)
            mGoogleApiClient?.disconnect()
        }
        mAuth!!.removeAuthStateListener(mAuthListener!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        //    mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_CANCELED && resultCode == Activity.RESULT_OK) {

            finish()

        } else if (requestCode == RC_SIGN_IN) { // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            connectGoogle(result)
            //   mPresenter.connectGoogle(result);
        }
    }

    private fun setupGoogleSign() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

    }

    private fun connectGoogle(result: GoogleSignInResult) {

        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            firebaseAuthWithGoogle(acct!!)
        } else {
            Log.d("Test", "handleSignInResult:" + result.isSuccess + " " + result.status)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (!task.isSuccessful) {
                        Log.w("Firebase", "signInWithCredential", task.exception)

                    }
                }
    }

    public override fun onPause() {
        super.onPause()
        mGoogleApiClient?.stopAutoManage(this)
        mGoogleApiClient?.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        mGoogleApiClient?.disconnect()
    }

    private fun signInByEmail(email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    Log.d("FragmentActivity", "signInWithEmail:onComplete:" + task.isSuccessful)
                    hideProgressDialog()
                    if (!task.isSuccessful) {
                        Log.d("FragmentActivity", "signInWithEmail:failed", task.exception)


                    } else {

                    }
                }

    }

    override fun onShowProgressDialog() {
        showProgressDialog()
    }

    override fun onHideProgressDialog() {
        hideProgressDialog()
    }

}
