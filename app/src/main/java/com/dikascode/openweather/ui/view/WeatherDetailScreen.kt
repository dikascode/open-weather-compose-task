package com.dikascode.openweather.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel

@Composable
fun WeatherDetailScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherState by viewModel.weatherState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        weatherState?.let { weather ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "City: ${weather.name ?: "N/A"}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Temperature: ${weather.main?.temp ?: "N/A"}K",
                    style = MaterialTheme.typography.headlineSmall
                )
                weather.weather?.firstOrNull()?.let {
                    Text(
                        text = "Description: ${it.description ?: "N/A"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        } ?: run {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
