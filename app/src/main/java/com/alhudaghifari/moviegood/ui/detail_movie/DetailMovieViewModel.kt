package com.alhudaghifari.moviegood.ui.detail_movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.local.MoviesData
import com.alhudaghifari.moviegood.data.local.RecommendationData
import com.alhudaghifari.moviegood.data.remote.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.MovieItem
import com.alhudaghifari.moviegood.data.remote.MovieResponse
import com.alhudaghifari.moviegood.ui.movies.MoviesViewModel
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {

    fun getRecommendationMovie(currentIdMovie: Int) : LiveData<Resource<List<MovieItem>>> = repository.getPopularMovies(currentIdMovie)

    fun getDetailMovie(id: String) : LiveData<Resource<MovieDetailResponse>>  = repository.getDetailMovie(id)
}