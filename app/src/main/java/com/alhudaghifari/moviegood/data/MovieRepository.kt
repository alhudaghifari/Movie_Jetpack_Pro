package com.alhudaghifari.moviegood.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.local.MovieLocalDataSource
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.remote.ApiResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.data.remote.source.MovieRemoteDataSource
import com.alhudaghifari.moviegood.utils.AppExecutors
import com.alhudaghifari.moviegood.vo.Resource
import com.alhudaghifari.moviegood.vo.Status
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataInterface {

    override fun getNowPlaying(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getNowPlayingMovie(),
                    config
                ).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieItem>>> {
                return remoteDataSource.getNowPlaying()
            }

            override fun saveCallResult(data: List<MovieItem>) {
                val movieList = ArrayList<MovieEntity>()

                for (response in data) {
                    val movie = response.id?.let {
                        MovieEntity(it.toString(), response.title ?: "", response.releaseDate ?: "",
                        "", response.voteAverage ?: 0.0,response.overview ?: "",
                            response.posterPath ?: "", "",
                        )
                    }
                    if (movie != null) {
                        movieList.add(movie)
                    }
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getPopularMovies(currentIdMovie: Int): LiveData<Resource<List<MovieItem>>> = remoteDataSource.getPopularMovies(currentIdMovie)

    override fun getDetailMovie(idMovie: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getDetailMovieById(idMovie)

            override fun shouldFetch(data: MovieEntity?): Boolean = data?.tagline.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> = remoteDataSource.getDetailMovie(idMovie)

            override fun saveCallResult(data: MovieDetailResponse) {
                val movie = data.let {
                    var category = "-"

                    it.genres?.let {
                        val genreSize = it.size
                        for (i in 0..genreSize-1) {
                            if (i == 0) {
                                category = it[i]?.name ?: ""
                            } else {
                                category += ", ${it[i]?.name ?: ""}"
                            }
                        }
                    }
                    MovieEntity(it.id.toString(), it.title ?: "", it.releaseDate ?: "",
                        category, it.voteAverage ?: 0.0,it.overview ?: "",
                        it.posterPath ?: "", it.tagline ?: "tagline_",
                    )
                }
                localDataSource.updateMovie(movie)
            }

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun setFavoriteMovies(movie: MovieEntity, isFavorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, isFavorite) }
    }

}