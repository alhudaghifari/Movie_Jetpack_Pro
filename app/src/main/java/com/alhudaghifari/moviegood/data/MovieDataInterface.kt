package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.vo.Resource

interface MovieDataInterface {
    fun getNowPlaying() : LiveData<Resource<PagedList<MovieEntity>>>
    fun getPopularMovies(currentIdMovie: Int) : LiveData<Resource<List<MovieItem>>>
    fun getDetailMovie(idMovie: String) : LiveData<Resource<MovieEntity>>
    fun getFavoriteMovies() : LiveData<PagedList<MovieEntity>>
    fun setFavoriteMovies(movie: MovieEntity, isFavorite: Boolean)
}