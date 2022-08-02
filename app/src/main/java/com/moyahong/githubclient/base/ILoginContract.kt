package com.moyahong.githubclient.base

import android.content.Intent
import com.moyahong.githubclient.base.mvp.IBaseContract

interface ILoginContract {

    interface View : IBaseContract.View {

        fun onGetTokenSuccess(basicToken: BasicToken)

        fun onGetTokenError(errorMsg: String)

        fun onLoginComplete()

        fun showProgressDialog(msg: String)

        fun dismissProgressDialog()
    }

    interface Presenter : IBaseContract.Presenter<View> {

        fun getToken(code: String, state: String)

        fun handleOauth(intent: Intent)

        fun getUserInfo(basicToken: BasicToken)

    }

}