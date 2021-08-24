package com.alhudaghifari.moviegood.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}