package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.local.TvLocalDataSource
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.data.remote.ApiResponse
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.data.remote.source.TvRemoteDataSource
import com.alhudaghifari.moviegood.utils.AppExecutors
import com.alhudaghifari.moviegood.vo.Resource
import javax.inject.Inject

class TvRepository @Inject constructor(
    private val remoteDataSource: TvRemoteDataSource,
    private val localDataSource: TvLocalDataSource,
    private val appExecutors: AppExecutors
    ) : TvDataInterface {
    override fun getOnTheAir(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getListTv(),
                    config
                ).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvItem>>> {
                return remoteDataSource.getOnTheAir()
            }

            override fun saveCallResult(data: List<TvItem>) {
                val tvList = ArrayList<TvEntity>()

                for (response in data) {
                    val tv = response.id?.let {
                        TvEntity(it.toString(), response.name ?: "", response.firstAirDate ?: "",
                            "", response.voteAverage ?: 0.0,response.overview ?: "",
                            response.posterPath ?: "", "",
                        )
                    }
                    if (tv != null) {
                        tvList.add(tv)
                    }
                }

                localDataSource.insertTv(tvList)
            }
        }.asLiveData()
    }

    override fun getPopularTv(currentId: Int): LiveData<Resource<List<TvItem>>> = remoteDataSource.getPopularTv(currentId)

    override fun getDetailTv(idTv: String): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> = localDataSource.getDetailTvById(idTv)

            override fun shouldFetch(data: TvEntity?): Boolean = data?.tagline.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<TvDetailResponse>> = remoteDataSource.getDetailTv(idTv)

            override fun saveCallResult(data: TvDetailResponse) {
                val tv = data.let {
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
                    TvEntity(it.id.toString(), it.name ?: "", it.firstAirDate ?: "",
                        category, it.voteAverage ?: 0.0,it.overview ?: "",
                        it.posterPath ?: "", it.tagline ?: "tagline_",
                    )
                }
                localDataSource.updateTv(tv)
            }

        }.asLiveData()
    }

    override fun getFavoriteTv(): LiveData<Resource<PagedList<TvEntity>>> {
        TODO("Not yet implemented")
    }

    override fun setFavoriteTv(tv: TvEntity, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

}