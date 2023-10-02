package com.example.tugasgithubuser.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
    @Query("q") username:String? = null
    ):Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username:String,
    ):Call<DetailuserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username:String
    ):Call<List<UserItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username:String
    ):Call<List<UserItem>>

}