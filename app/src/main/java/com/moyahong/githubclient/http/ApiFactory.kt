package com.moyahong.githubclient.http

import android.text.TextUtils
import android.util.Log
import androidx.annotation.NonNull
import com.moyahong.githubclient.AppData
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.GsonUtils
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
object ApiFactory {
    private const val TAG = "ApiFactory"

    private var token: String? = null

    /**
     * 创建API Service接口实例
     */
    fun <T> create(baseUrl: String, clazz: Class<T>, token: String?): T {
        this.token = token
        return Retrofit.Builder().baseUrl(baseUrl).client(newClient())
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.mGson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build().create(clazz)
    }

    /**
     * 创建API Service接口实例
     */
    fun <T> createForToken(baseUrl: String, clazz: Class<T>, token: String?): T {
        this.token = token
        return Retrofit.Builder().baseUrl(baseUrl).client(newClient())
            .addConverterFactory(StringConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build().create(clazz)
    }


    /**
     * OkHttpClient客户端
     */
    private fun newClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)// 连接时间：30s超时
        .readTimeout(10, TimeUnit.SECONDS)// 读取时间：10s超时
        .writeTimeout(10, TimeUnit.SECONDS)// 写入时间：10s超时
        .addInterceptor(BaseInterceptor())
//        .addNetworkInterceptor(NetworkBaseInterceptor())
        .build()

    private class BaseInterceptor : Interceptor {
        @kotlin.jvm.Throws(IOException::class)
        override fun intercept(@NonNull chain: Interceptor.Chain): Response {
            var request: Request = chain.request()

            //add unique login id in url to differentiate caches
            if (AppData.loggedUser != null
                && !AppConfig.isCommonPageUrl(request.url.toString())
            ) {
                val url = request.url.newBuilder()
                    .addQueryParameter(
                        "uniqueLoginId",
                        AppData.loggedUser?.login
                    )
                    .build()
                request = request.newBuilder()
                    .url(url)
                    .build()
            }

            //add access token
            if (!TextUtils.isEmpty(token)) {
                val auth = if (token!!.startsWith("Basic")) token else "token $token"
                request = request.newBuilder()
                    .addHeader("Authorization", auth!!)
                    .build()
            }
            Log.d(TAG, request.url.toString())

            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build()

            //第二次请求，强制使用网络请求
//            val forceNetWork = request.header("forceNetWork")
            //有forceNetWork且无网络状态下取从缓存中取
//            if ("true" == forceNetWork) {
//                request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_NETWORK)
//                    .build()
//            }else if (!TextUtils.isEmpty(forceNetWork)) {
//                request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build()
//            }
            return chain.proceed(request)
        }
    }

    private class NetworkBaseInterceptor : Interceptor {
        @kotlin.jvm.Throws(IOException::class)
        override fun intercept(@NonNull chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val originalResponse: Response = chain.proceed(request)

//            String serverCacheControl = originalResponse.header("Cache-Control");
            var requestCacheControl = request.cacheControl.toString()

            //有forceNetWork时，强制更改缓存策略
            val forceNetWork = request.header("forceNetWork")
            if (!TextUtils.isEmpty(forceNetWork)) {
                requestCacheControl = getCacheString()
            }
            return if (TextUtils.isEmpty(requestCacheControl)) {
                originalResponse
            } else {
                originalResponse.newBuilder()
                    .header("Cache-Control", requestCacheControl) //纠正服务器时间，服务器时间出错时可能会导致缓存处理出错
                    //                        .header("Date", getGMTTime())
                    .removeHeader("Pragma")
                    .build()
            }
        }
    }

    fun getCacheString(): String {
        return "public, max-age=" + AppConfig.CACHE_MAX_AGE
    }


}