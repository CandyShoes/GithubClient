package com.moyahong.githubclient.base.mvp

import android.content.Context
import android.os.Bundle

interface IBaseContract {

    interface View {

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