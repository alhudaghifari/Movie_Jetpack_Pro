package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import com.alhudaghifari.moviegood.data.remote.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.MovieItem
import com.alhudaghifari.moviegood.data.remote.MovieResponse
import com.alhudaghifari.moviegood.utils.Resource

interface MovieDataInterface {
    fun getNowPlaying() : LiveData<Resource<MovieResponse>>
    fun getPopularMovies(currentIdMovie: Int) : LiveData<Resource<List<MovieItem>>>
    fun getDetailMovie(idMovie: String) : LiveData<Resource<MovieDetailResponse>>
}