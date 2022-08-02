package com.moyahong.githubclient.http

import androidx.annotation.NonNull
import com.moyahong.githubclient.model.SearchResult
import com.moyahong.githubclient.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    //    https://api.github.com/search/users?q=Ray&sort=followers&order=desc
    @NonNull
    @GET("search/users")
    fun searchUsers(
        @Query(value = "q", encoded = true) query: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int
    ): Deferred<Response<SearchResult<User>>>

//    @NonNull
//    @GET("search/repositories")
//    fun searchRepos(
//        @Query(value = "q", encoded = true) query: String?,
//        @Query("sort") sort: String?,
//        @Query("order") order: String?,
//        @Query("page") page: Int
//    ): Deferred<Response<SearchResult<Repository?>?>?>?
//
//    //    https://api.github.com/search/issues?sort=created&page=1&q=user:ThirtyDegreesRay+state:open&order=desc
//    @NonNull
//    @GET("search/issues")
//    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
//    fun searchIssues(
//        @Header("forceNetWork") forceNetWork: Boolean,
//        @Query(value = "q", encoded = true) query: String?,
//        @Query("sort") sort: String?,
//        @Query("order") order: String?,
//        @Query("page") page: Int
//    ): Deferred<Response<SearchResult<Issue?>?>?>?
}