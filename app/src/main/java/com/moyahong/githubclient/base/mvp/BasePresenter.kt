package com.moyahong.githubclient.base.mvp

import android.content.Context
import androidx.fragment.app.Fragment


abstract class BasePresenter<V : IBaseContract.View> {
    private val TAG = "BasePresenter"

    //View
    protected var mView: V? = null

    private val isEventSubscriber = false
    protected var isViewInitialized = false
        private set
    private var isAttached = false

    /**
     * 绑定View
     *
     * @param view view
     */

    fun attachView(view: V) {
        mView = view
        onViewAttached()
        isAttached = true
    }

    private fun onViewAttached() {}

    /**
     * 取消View绑定
     */
    fun detachView() {
        mView = null
    }

    fun onViewInitialized() {
        isViewInitialized = true
    }

    /**
     * 获取上下文，需在onViewAttached()后调用
     *
     * @return
     */
    val context: Context?
        get() = when (mView) {
            is Context -> {
                mView as Context
            }
            is Fragment -> {
                (mView as Fragment).context!!
            }
            else -> {
                null
            }
        }

}