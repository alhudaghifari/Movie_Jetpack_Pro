package com.alhudaghifari.moviegood.data.local

import androidx.paging.DataSource
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.room.AppDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(private val dao: AppDao){
    fun getNowPlayingMovie(): DataSource.Factory<Int, MovieEntity> =
        dao.getNowPlayingMovie()

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> =
        dao.getFavoriteMovie()

    fun insertMovies(movies: List<MovieEntity>) = dao.insertMovie(movies)

    fun insertAMovie(movie: MovieEntity) = dao.insertAMovie(movie)

    fun getDetailMovieById(id: String) = dao.getDetailMovieById(id)

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        movie.isFavorite = state
        dao.updateMovie(movie)
    }
}