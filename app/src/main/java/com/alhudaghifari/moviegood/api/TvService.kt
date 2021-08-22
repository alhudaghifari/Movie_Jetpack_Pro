package com.alhudaghifari.moviegood.api

import com.alhudaghifari.moviegood.BuildConfig
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvService {
    @GET("tv/on_the_air")
    fun getOnTheAir(
        @Query("api_key") query: String = BuildConfig.k,
    ): Call<TvResponse>

    @GET("tv/popular")
    fun getPopular(
        @Query("api_key") query: String = BuildConfig.k,
    ): Call<TvResponse>

    @GET("tv/{id}}")
    fun getDetailTv(
        @Path("id") idTv: String,
        @Query("api_key") query: String = BuildConfig.k,
    ): Call<TvDetailResponse>

    companion object {
        fun create(): TvService {
            val api = ApiConfig.create()
            return api.create(TvService::class.java)
        }
    }
}