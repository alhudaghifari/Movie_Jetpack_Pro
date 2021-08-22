package com.alhudaghifari.moviegood.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {

    fun getRecommendationMovie(currentIdMovie: Int) : LiveData<Resource<List<MovieItem>>> = repository.getPopularMovies(currentIdMovie)

    fun getDetailMovie(id: String) : LiveData<Resource<MovieDetailResponse>>  = repository.getDetailMovie(id)
}