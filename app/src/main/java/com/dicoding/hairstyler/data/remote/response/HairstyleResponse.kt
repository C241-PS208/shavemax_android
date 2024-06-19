package com.dicoding.hairstyler.data.remote.response

import com.google.gson.annotations.SerializedName

data class HairstyleResponse(

    @field:SerializedName("HairstyleResponse")
    val hairstyleResponse: List<HairstyleResponseItem>
)

data class HairstyleResponseItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("photo_url")
    val photoUrl: String
)