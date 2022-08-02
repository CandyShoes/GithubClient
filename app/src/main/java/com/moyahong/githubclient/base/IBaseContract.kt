package com.moyahong.githubclient.base

import android.content.Context
import android.os.Bundle

interface IBaseContract {

    interface View {



        fun showToast(message: String)

        fun showInfoToast(message: String)

        fun showSuccessToast(message: String)

        fun showErrorToast(message: String)

        fun showWarningToast(message: String)

        fun showLoading()

        fun hideLoading()


    }

    interface Presenter<V : View> {


        val context: Context?

        fun onSaveInstanceState(outState: Bundle)

        fun onRestoreInstanceState(outState: Bundle)

        fun attachView(view: V)

        fun detachView()

        fun onViewInitialized()
    }

}