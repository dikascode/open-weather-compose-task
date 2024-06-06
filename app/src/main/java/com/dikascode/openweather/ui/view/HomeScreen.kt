package com.dikascode.openweather.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dikascode.openweather.ui.viewmodel.WeatherState
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val weatherState by viewModel.weatherState.collectAsState()
    var fetchTriggered by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (city.text.trim().isEmpty()) {
                    Toast.makeText(context, "Please enter a city name", Toast.LENGTH_SHORT).show()
                } else {
                    fetchTriggered = true
                    viewModel.fetchWeather(city.text)
                }
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Get Weather")
        }

        when (weatherState) {
            is WeatherState.Loading -> {
                Spacer(modifier = Modifier.height(16.dp))
//                CircularProgressIndicator()
                Text("Loading...")
            }
            is WeatherState.Error -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Error: ${(weatherState as WeatherState.Error).message}")
            }
            is WeatherState.Empty -> {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text("No data available")
            }
            is WeatherState.Success -> {
                if (fetchTriggered) {
                    LaunchedEffect(Unit) {
                        navController.navigate("weatherDetail") {
                            popUpTo("home") { inclusive = false }
                        }
                        fetchTriggered = false
                    }
                }
            }
        }
    }

    LaunchedEffect(weatherState, fetchTriggered) {
        if (fetchTriggered && weatherState is WeatherState.Error) {
            Toast.makeText(context, "Error: ${(weatherState as WeatherState.Error).message}", Toast.LENGTH_LONG).show()
            fetchTriggered = false
        }
    }
}
