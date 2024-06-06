package com.dikascode.openweather.di

import com.dikascode.openweather.data.network.ApiService
import com.dikascode.openweather.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: ApiService): WeatherRepository {
        return WeatherRepository(apiService)
    }
}
