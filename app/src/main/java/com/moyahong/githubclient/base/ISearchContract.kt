package com.moyahong.githubclient.base

import com.moyahong.githubclient.base.mvp.IBaseContract
import com.moyahong.githubclient.model.SearchResult
import com.moyahong.githubclient.model.User

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class ISearchContract {
    interface View : IBaseContract.View {

        fun onSearchSuccess(result: SearchResult<User>)

        fun onSearchError(errorMsg: String)

        fun showProgressDialog(msg: String)

        fun dismissProgressDialog()
    }

    interface Presenter {

        fun search(text: String)

    }
}