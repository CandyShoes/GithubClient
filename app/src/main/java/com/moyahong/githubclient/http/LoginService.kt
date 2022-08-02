package com.moyahong.githubclient.http

import com.moyahong.githubclient.base.OauthToken
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface LoginService {

    //https://github.com/login/oauth/access_token?client_id=8f7213694e115df205fb&client_secret=82c57672382db5c7b528d79e283c398ad02e3c3f&code=286ce2cd6733277f3ecd&state=761ad118-d2a3-4627-948d-accceb7f2598
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    fun getAccessToken(
        @Query("client_id") clientId: String?,
        @Query("client_secret") clientSecret: String?,
        @Query("code") code: String?,
        @Query("state") state: String?
    ): Deferred<Response<OauthToken>>
}