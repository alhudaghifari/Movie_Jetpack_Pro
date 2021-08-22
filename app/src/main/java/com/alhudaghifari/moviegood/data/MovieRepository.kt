package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.data.remote.source.MovieDataSource
import com.alhudaghifari.moviegood.vo.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val dataSource: MovieDataSource
) : MovieDataInterface {

    override fun getNowPlaying(): LiveData<Resource<MovieResponse>> = dataSource.getNowPlaying()

    override fun getPopularMovies(currentIdMovie: Int): LiveData<Resource<List<MovieItem>>> = dataSource.getPopularMovies(currentIdMovie)

    override fun getDetailMovie(idMovie: String): LiveData<Resource<MovieDetailResponse>> = dataSource.getDetailMovie(idMovie)

}