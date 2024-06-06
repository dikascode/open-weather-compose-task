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
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, "")
                _weatherState.value = response
                _errorState.value = null
            } catch (e: Exception) {
                _weatherState.value = null
                _errorState.value = e.message
            }
        }
    }
}
