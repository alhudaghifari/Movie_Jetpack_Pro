package com.alhudaghifari.moviegood.di

import com.alhudaghifari.moviegood.api.MovieService
import com.alhudaghifari.moviegood.api.TvService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideMovieService(): MovieService = MovieService.create()

    @Singleton
    @Provides
    fun provideTvService(): TvService = TvService.create()
}