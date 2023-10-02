package com.example.tugasgithubuser.API

import com.google.gson.annotations.SerializedName

data class FollowResponse(
@field:SerializedName("FollowResponse")
    val followResponse:List<FollowItem>
)
data class FollowItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

)
