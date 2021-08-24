package com.alhudaghifari.moviegood.ui.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> = repository.getFavoriteMovies()

    fun setFavorite(movie: MovieEntity, isFavorite: Boolean) = repository.setFavoriteMovies(movie, isFavorite)
}