package com.moyahong.githubclient.base

import com.google.gson.annotations.SerializedName


class OauthToken {
    companion object {
        //access_token=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&scope=gist%2Cnotifications%2Crepo%2Cuser&token_type=bearer
        fun parse(text: String?): OauthToken? {
            if (text == null) {
                return null
            }
            val oauthToken = OauthToken()
            try {
                text.split("&").forEach {
                    val pair = it.split("=")
                    when (pair[0]) {
                        "access_token" -> {
                            oauthToken.accessToken = pair[1]
                        }
                        "scope" -> {
                            oauthToken.scope = pair[1]
                        }
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return oauthToken
        }
    }

    @SerializedName("access_token")
    var accessToken: String? = null

    var scope: String? = null

    override fun toString(): String {
        return "{accessToken:$accessToken,scope:$scope}"
    }
}