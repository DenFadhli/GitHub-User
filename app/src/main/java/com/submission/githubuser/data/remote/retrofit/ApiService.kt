package com.submission.githubuser.data.remote.retrofit

import com.submission.githubuser.GitHubResponse
import com.submission.githubuser.ItemsItem
import com.submission.githubuser.data.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") user : String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") followers: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingsUser(
        @Path("username") following: String
    ): Call<List<ItemsItem>>

}