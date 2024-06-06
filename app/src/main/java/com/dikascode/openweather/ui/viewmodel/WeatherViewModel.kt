package com.dikascode.openweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikascode.openweather.data.model.WeatherResponse
import com.dikascode.openweather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val response = repository.getWeather(city, "f58af07537a499690ecac246fae866a0")
                _weatherState.value = response
                _errorState.value = null
            } catch (e: Exception) {
                _weatherState.value = null
                _errorState.value = e.message
            } finally {
                _loadingState.value = false
            }
        }
    }
}
