package com.moyahong.githubclient.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.moyahong.githubclient.AppOpener
import com.moyahong.githubclient.R
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.BasicToken
import com.moyahong.githubclient.base.ILoginContract
import com.moyahong.githubclient.profile.PersonActivity
import es.dmoral.toasty.Toasty
import java.util.*

class LoginActivity : Activity(), ILoginContract.View {
    private var userEt: EditText? = null
    private var passwordEt: EditText? = null
    private var loginBt: Button? = null
    private val mPresenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBt = findViewById(R.id.bt_login)
        loginBt?.setOnClickListener {
            AppOpener.openInBrowser(this, getOAuth2Url())
        }
        mPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getOAuth2Url(): String {
        val randomState = UUID.randomUUID().toString()
        return AppConfig.OAUTH2_URL +
                "?client_id=" + AppConfig.OPENHUB_CLIENT_ID +
                "&scope=" + AppConfig.OAUTH2_SCOPE +
                "&state=" + randomState
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            mPresenter.handleOauth(intent)
        }
        setIntent(null)
    }

    fun sendLoginService() {

    }

    private fun checkInvalid(): Boolean {
        return !TextUtils.isEmpty(userEt?.text) && !TextUtils.isEmpty(passwordEt?.text)
    }


    override fun onGetTokenSuccess(basicToken: BasicToken) {
        mPresenter.getUserInfo(basicToken)
    }

    override fun onGetTokenError(errorMsg: String) {
        Handler().postDelayed({
//            loginBn.reset()
//            loginBn.setEnabled(true)
        }, 1000)

        Toasty.error(applicationContext, errorMsg).show()
    }

    override fun onLoginComplete() {
        loginBt?.postDelayed({
            finish()
            startActivity(Intent(this, PersonActivity::class.java))
        }, 1000L)
    }

    override fun showProgressDialog(msg: String) {
//        Toasty.normal(this, msg)
    }

    override fun dismissProgressDialog() {

    }
}