package com.moyahong.githubclient.http

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
object RetrofitUtils {

    suspend fun <T> request(deferred: Deferred<Response<T>>): Response<T>? = withContext(Dispatchers.IO) {
        try {
            val response = deferred.await()
            response
        } catch (e: Exception) {
            // 这里统一处理错误
            e.printStackTrace()
            null
        }
    }

    suspend fun <T> requestToken(deferred: Deferred<Response<T>>): Response<T>? = withContext(Dispatchers.IO) {
        try {
            val response = deferred.await()
            response
        } catch (e: Exception) {
            // 这里统一处理错误
            e.printStackTrace()
            null
        }
    }
}