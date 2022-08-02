package com.moyahong.githubclient.http

import androidx.annotation.NonNull
import com.moyahong.githubclient.model.User
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserService {
    @NonNull
    @GET("user")
    fun getPersonInfo(
        @Header("forceNetWork") forceNetWork: Boolean
    ): Deferred<Response<User>>

    //https://api.github.com/users/bbondy
    @NonNull
    @GET("users/{user}")
    fun getUser(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String?
    ): Deferred<Response<User>>

    @NonNull
    @GET("user/following/{user}")
    fun checkFollowing(
        @Path("user") user: String?
    ): Deferred<Response<ResponseBody>>

}