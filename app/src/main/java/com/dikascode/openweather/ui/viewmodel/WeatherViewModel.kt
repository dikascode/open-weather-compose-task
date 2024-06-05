package com.dikascode.openweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikascode.openweather.data.model.WeatherResponse
import com.dikascode.openweather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, "f58af07537a499690ecac246fae866a0")
                _weatherState.value = response
            } catch (e: Exception) {
                // Handle exception and possibly update the state with an error message
                _weatherState.value = null
            }
        }
    }
}
