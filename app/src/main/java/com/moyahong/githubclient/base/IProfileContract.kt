package com.moyahong.githubclient.base

import com.moyahong.githubclient.base.mvp.IBaseContract
import com.moyahong.githubclient.model.User

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class IProfileContract {
    interface View : IBaseContract.View {

        fun onQueryUserSuccess(result: User)

        fun onQueryUserError(errorMsg: String)

        fun showProgressDialog(msg: String)

        fun dismissProgressDialog()
    }

    interface Presenter {

        fun queryUser(user: String)
        fun logout()

    }
}