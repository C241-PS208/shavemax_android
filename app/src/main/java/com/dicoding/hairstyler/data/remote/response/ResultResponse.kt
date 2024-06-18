package com.dicoding.hairstyler.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultResponse(

	@field:SerializedName("hair_type")
	val hairType: String? = null,

	@field:SerializedName("face_type")
	val faceType: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem>? = null
) : Parcelable

@Parcelize
data class RecommendationsItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null
) : Parcelable
