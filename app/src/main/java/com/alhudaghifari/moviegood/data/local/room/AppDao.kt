package com.alhudaghifari.moviegood.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.entity.TvEntity

@Dao
interface AppDao {
    @Update
    fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie_table ")
    fun getNowPlayingMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_table WHERE movieId = :movieId")
    fun getDetailMovieById(movieId: String): LiveData<MovieEntity>

    @Query("SELECT * FROM movie_table where isFavorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Update
    fun updateTv(tvShows: TvEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(movies: List<TvEntity>)

    @Query("SELECT * FROM tv_table ")
    fun getListTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tv_table where isFavorite = 1")
    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tv_table WHERE tvId = :tvId")
    fun getDetailTvById(tvId: String): LiveData<TvEntity>

}