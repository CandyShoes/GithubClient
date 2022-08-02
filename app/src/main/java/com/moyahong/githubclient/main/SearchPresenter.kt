package com.moyahong.githubclient.main

import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.ISearchContract
import com.moyahong.githubclient.base.mvp.BasePresenter
import com.moyahong.githubclient.http.ApiFactory
import com.moyahong.githubclient.http.RetrofitUtils
import com.moyahong.githubclient.http.SearchService
import kotlinx.coroutines.runBlocking

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class SearchPresenter : BasePresenter<ISearchContract.View>(), ISearchContract.Presenter {

    override fun search(text: String) {
        runBlocking {
            mView?.showProgressDialog("loading...")
            val response = RetrofitUtils.request(
                getSearchService(null).searchUsers(text, "followers", "desc", 0)
            )
            if (response?.isSuccessful == true) {
                mView?.onSearchSuccess(response.body()!!)
            } else {
                mView?.onSearchError(response?.message() ?: "")
            }
        }
    }

    private fun getSearchService(token: String?) =
        ApiFactory.create(AppConfig.GITHUB_API_BASE_URL, SearchService::class.java, token)

}