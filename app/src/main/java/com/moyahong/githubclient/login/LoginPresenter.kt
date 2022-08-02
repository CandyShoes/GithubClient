package com.moyahong.githubclient.login

import android.content.Context
import android.content.Intent
import android.util.Log
import com.moyahong.githubclient.AppData
import com.moyahong.githubclient.BuildConfig
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.BasicToken
import com.moyahong.githubclient.base.ILoginContract
import com.moyahong.githubclient.base.mvp.BasePresenter
import com.moyahong.githubclient.http.ApiFactory
import com.moyahong.githubclient.http.LoginService
import com.moyahong.githubclient.http.RetrofitUtils
import com.moyahong.githubclient.http.UserService
import com.moyahong.githubclient.model.User
import kotlinx.coroutines.runBlocking


class LoginPresenter : BasePresenter<ILoginContract.View>() {

    private val TAG = "LoginPresenter"
    fun handleOauth(intent: Intent) {
        val uri = intent.data
        if (uri != null) {
            Log.e(TAG, uri.toString())
            val code = uri.getQueryParameter("code")
            val state = uri.getQueryParameter("state")
            getToken(code, state)
        }
    }

    fun getUserInfo(basicToken: BasicToken) {
        runBlocking {
            val response = RetrofitUtils.request(
                getUserService(basicToken.token).getPersonInfo(true)
            )
            if (response?.isSuccessful == true) {
//                        mView.dismissProgressDialog();
                saveAuthUser(basicToken, response.body())
                mView?.onLoginComplete()
            } else {
                mView?.dismissProgressDialog()
                mView?.onGetTokenError(response?.message() ?: "Unknown")
            }
        }
    }

    private fun saveAuthUser(basicToken: BasicToken, user: User?) {
        Log.e(TAG, "saveAuthUser：$basicToken,  $user")
        AppData.saveAuthUser(basicToken, user)
    }

    fun getToken(code: String, state: String) {
        runBlocking {
            mView?.showProgressDialog("loading...")
            val response = RetrofitUtils.request(
                getLoginService(null).getAccessToken(
                    BuildConfig.OPENHUB_CLIENT_ID,
                    BuildConfig.OPENHUB_CLIENT_SECRET, code, state
                )
            )
            val token = response?.body()
            Log.e(TAG, response?.body()?.toString() ?: "empty body")
            if (token != null) {
                Log.e(TAG, token.toString())
                mView?.onGetTokenSuccess(BasicToken.generateFromOauthToken(token))
            } else {
                mView?.onGetTokenError(response?.message() ?: "Unknown")
            }
        }
    }

    fun getLoginService(token: String?) =
        ApiFactory.create(AppConfig.GITHUB_BASE_URL, LoginService::class.java, token)

    fun getUserService(token: String?) =
        ApiFactory.create(AppConfig.GITHUB_API_BASE_URL, UserService::class.java, token)

    fun logout(context: Context) {

    }
}