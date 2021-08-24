package com.alhudaghifari.moviegood.di

import android.app.Application
import androidx.room.Room
import com.alhudaghifari.moviegood.data.local.room.AppDao
import com.alhudaghifari.moviegood.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Application): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "App-db-moviegood"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): AppDao = database.appDao()
}