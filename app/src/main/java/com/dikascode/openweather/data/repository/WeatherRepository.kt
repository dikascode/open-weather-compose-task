package com.dikascode.openweather.data.repository

import com.dikascode.openweather.data.model.WeatherResponse
import com.dikascode.openweather.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getWeather(city: String, apiKey: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, apiKey, "metric") //metric unit to get celsius
            if (response.cod != 200) {
                Result.Error("HTTP error: ${response.cod}")
            } else {
                Result.Success(response)
            }
        } catch (e: HttpException) {
            Result.Error("HTTP error: ${e.code()}")
        } catch (e: IOException) {
            Result.Error("Network error")
        } catch (e: Exception) {
            Result.Error("Unknown error")
        }
    }
}
