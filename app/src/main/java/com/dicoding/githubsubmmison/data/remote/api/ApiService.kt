package com.dicoding.githubsubmmison.data.remote.api

import com.dicoding.githubsubmmison.BuildConfig
import com.dicoding.githubsubmmison.data.remote.response.DetailResponse
import com.dicoding.githubsubmmison.data.remote.response.GithubResponse
import com.dicoding.githubsubmmison.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}