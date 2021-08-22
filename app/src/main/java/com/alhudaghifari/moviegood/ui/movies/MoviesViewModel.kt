package com.alhudaghifari.moviegood.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.remote.MovieResponse
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {

    fun getNowPlaying() : LiveData<Resource<MovieResponse>>  = repository.getNowPlaying()

}