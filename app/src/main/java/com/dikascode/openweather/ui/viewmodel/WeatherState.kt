package com.dikascode.openweather.ui.viewmodel

import com.dikascode.openweather.data.model.WeatherResponse

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weather: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
    object Empty : WeatherState()
}
