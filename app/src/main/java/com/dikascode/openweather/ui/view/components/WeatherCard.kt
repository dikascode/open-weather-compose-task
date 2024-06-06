package com.dikascode.openweather.ui.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dikascode.openweather.data.model.WeatherResponse

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "City: ${weather.name ?: "N/A"}",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Temperature: ${weather.main?.temp ?: "N/A"} °C",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            weather.weather?.firstOrNull()?.let {
                Text(
                    text = "Description: ${it.description ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                text = "Feels like: ${weather.main?.feels_like ?: "N/A"} °C",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Min Temperature: ${weather.main?.temp_min ?: "N/A"} °C",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Max Temperature: ${weather.main?.temp_max ?: "N/A"} °C",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
