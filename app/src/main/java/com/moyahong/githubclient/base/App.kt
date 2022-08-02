package com.moyahong.githubclient.base

import android.app.Application
import android.content.Context
import com.moyahong.githubclient.AppData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {
    companion object {
        lateinit var mInstance: App
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mInstance = this
        GlobalScope.launch {
            AppData.loadUser()
        }
    }
}