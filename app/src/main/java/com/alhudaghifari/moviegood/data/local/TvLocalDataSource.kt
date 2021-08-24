package com.alhudaghifari.moviegood.data.local

import androidx.paging.DataSource
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.data.local.room.AppDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvLocalDataSource @Inject constructor(private val dao: AppDao){
    fun getListTv(): DataSource.Factory<Int, TvEntity> =
        dao.getListTv()

    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity> =
        dao.getFavoriteTv()

    fun getDetailTvById(id: String) = dao.getDetailTvById(id)

    fun insertTv(tvShows: List<TvEntity>) = dao.insertTv(tvShows)

    fun insertATv(tv: TvEntity) = dao.insertATv(tv)

    fun updateTv(tv: TvEntity) = dao.updateTv(tv)

    fun setFavoriteTv(tv: TvEntity, isFavorite: Boolean) {
        tv.isFavorite = isFavorite
        dao.updateTv(tv)
    }
}