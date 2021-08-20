package com.alhudaghifari.moviegood.api

import com.alhudaghifari.moviegood.data.remote.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") query: String = ApiConstant.k,
    ): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") query: String = ApiConstant.k,
    ): Call<MovieResponse>

    @GET("movie/{id}}")
    fun getDetailMovie(
        @Path("id") idMovie: String,
        @Query("api_key") query: String = ApiConstant.k,
    ): Call<MovieDetailResponse>

    companion object {
        fun create(): MovieService {
            val api = ApiConfig.create()
            return api.create(MovieService::class.java)
        }
    }
}