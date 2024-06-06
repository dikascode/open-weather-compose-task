package com.dikascode.openweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikascode.openweather.data.model.WeatherResponse
import com.dikascode.openweather.data.repository.Result
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
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val weatherState: StateFlow<WeatherState> = _weatherState

    companion object {
        const val API_KEY = "f58af07537a499690ecac246fae866a0"
    }

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading

            //in a production environment this key won't be passed here leaving it as it is for test
            when (val result = repository.getWeather(city, API_KEY)) {
                is Result.Success -> {
                    _weatherState.value = WeatherState.Success(result.data)
                }
                is Result.Error -> {
                    _weatherState.value = WeatherState.Error(result.message)
                }
            }
        }
    }
}
