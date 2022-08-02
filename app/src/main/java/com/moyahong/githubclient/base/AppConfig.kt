package com.moyahong.githubclient.base

import android.text.TextUtils
import java.util.*

object AppConfig {
    const val GITHUB_BASE_URL = "https://github.com/"
    const val GITHUB_API_BASE_URL = "https://api.github.com/"
    const val USER_SP = "user_sp"
    const val USER_SP_KEY = "user_sp_key"
    const val BASIC_TOKEN_SP_KEY = "basic_token_sp_key"
    const val INTENT_USER = "intent_user"

    /**
     * This link are for OpenHub only. Please do not use this endpoint in your applications.
     * If you want to get trending repositories, you may stand up your own instance.
     * https://github.com/thedillonb/GitHub-Trending
     */
    const val CACHE_MAX_AGE = 4 * 7 * 24 * 60 * 60
    val OPENHUB_CLIENT_ID: String = "8f7213694e115df205fb"
    val OPENHUB_CLIENT_SECRET: String = "82c57672382db5c7b528d79e283c398ad02e3c3f"
    const val OAUTH2_SCOPE = "user,repo,gist,notifications"
    const val OAUTH2_URL = GITHUB_BASE_URL + "login/oauth/authorize"
    val COMMON_PAGE_URL_LIST = Arrays.asList(
        "https://github.com/trending"
    )

    fun isCommonPageUrl(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        for (commonUrl in COMMON_PAGE_URL_LIST) {
            if (url.contains(commonUrl!!)) {
                return true
            }
        }
        return false
    }
}