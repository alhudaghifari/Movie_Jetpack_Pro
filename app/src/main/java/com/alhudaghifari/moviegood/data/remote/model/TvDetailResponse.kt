package com.alhudaghifari.moviegood.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetailResponse(

    @field:SerializedName("overview")
	val overview: String? = null,

    @field:SerializedName("original_language")
	val originalLanguage: String? = null,

    @field:SerializedName("number_of_episodes")
	val numberOfEpisodes: Int? = null,

    @field:SerializedName("created_by")
	val createdBy: List<CreatedByItem?>? = null,

    @field:SerializedName("poster_path")
	val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

    @field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

    @field:SerializedName("original_name")
	val originalName: String? = null,

    @field:SerializedName("popularity")
	val popularity: Double? = null,

    @field:SerializedName("vote_average")
	val voteAverage: Double? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("tagline")
	val tagline: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("number_of_seasons")
	val numberOfSeasons: Int? = null,

    @field:SerializedName("in_production")
	val inProduction: Boolean? = null,

    @field:SerializedName("vote_count")
	val voteCount: Int? = null,

    @field:SerializedName("homepage")
	val homepage: String? = null
) : Parcelable

@Parcelize
data class CreatedByItem(

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("credit_id")
	val creditId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
