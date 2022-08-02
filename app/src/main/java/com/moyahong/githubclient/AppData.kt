package com.moyahong.githubclient

import android.content.Context
import com.moyahong.githubclient.base.App
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.BasicToken
import com.moyahong.githubclient.base.GsonUtils
import com.moyahong.githubclient.model.User
import java.util.*

object AppData {

    var loggedUser: User? = null
    var basicToken: BasicToken? = null
    private var systemDefaultLocal: Locale? = null
    fun getSystemDefaultLocal(): Locale? {
        if (systemDefaultLocal == null) {
            systemDefaultLocal = Locale.getDefault()
        }
        return systemDefaultLocal
    }

    fun saveAuthUser(basicToken: BasicToken, user: User?) {
        loggedUser = user
        this.basicToken = basicToken
        val sp = App.mInstance.getSharedPreferences(AppConfig.USER_SP, Context.MODE_PRIVATE)
        sp.edit()
            .putString(AppConfig.USER_SP_KEY, GsonUtils.mGson.toJson(user))
            .putString(AppConfig.BASIC_TOKEN_SP_KEY, GsonUtils.mGson.toJson(basicToken))
            .apply()
    }

    fun logout() {
        val sp = App.mInstance.getSharedPreferences(AppConfig.USER_SP, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
        loggedUser = null
    }

    fun loadUser() {
        val sp = App.mInstance.getSharedPreferences(AppConfig.USER_SP, Context.MODE_PRIVATE)
        val userJson = sp.getString(AppConfig.USER_SP_KEY, "")
        if (userJson?.isNotEmpty() == true) {
            loggedUser = GsonUtils.mGson.fromJson(userJson, User::class.java)
        }
        val basicTokenJson = sp.getString(AppConfig.USER_SP_KEY, "")
        if (basicTokenJson?.isNotEmpty() == true) {
            basicToken = GsonUtils.mGson.fromJson(basicTokenJson, BasicToken::class.java)
        }
    }

    fun isLogin(): Boolean {
        return loggedUser?.login?.isNotEmpty() == true
    }
}