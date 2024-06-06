package com.dikascode.openweather.data.repository

import com.dikascode.openweather.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getWeather(city: String, apiKey: String) = apiService.getWeather(city, apiKey, "metric") //metric unit to get celsius
}
