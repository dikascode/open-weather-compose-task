package com.dikascode.openweather.data.repository

import com.dikascode.openweather.data.network.ApiClient

class WeatherRepository {
    private val apiService = ApiClient.getApiService()

    suspend fun getWeather(city: String, apiKey: String) = apiService.getWeather(city, apiKey)
}
