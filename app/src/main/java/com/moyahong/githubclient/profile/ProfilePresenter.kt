package com.moyahong.githubclient.profile

import android.util.Log
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.IProfileContract
import com.moyahong.githubclient.base.mvp.BasePresenter
import com.moyahong.githubclient.http.ApiFactory
import com.moyahong.githubclient.http.RetrofitUtils
import com.moyahong.githubclient.http.UserService
import kotlinx.coroutines.runBlocking

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class ProfilePresenter : BasePresenter<IProfileContract.View>(), IProfileContract.Presenter {
    private var mService: UserService? = null
    private val TAG = "ProfilePresenter"
    override fun queryUser(user: String) {
        runBlocking {
            mView?.showProgressDialog("loading...")
            val response = RetrofitUtils.request(getUserService(null).getUser(true, user))
            Log.e(TAG, response.toString())
            if (response?.isSuccessful == true) {
                response.body()?.let { mView?.onQueryUserSuccess(it) }
            } else {
                mView?.onQueryUserError(response?.message() ?: "")
            }
        }
    }

    override fun logout() {

    }

    private fun getUserService(token: String?): UserService {
        if (mService == null) {
            mService =
                ApiFactory.create(AppConfig.GITHUB_API_BASE_URL, UserService::class.java, token)
        }
        return mService as UserService
    }

}