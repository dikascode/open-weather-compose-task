package com.dikascode.openweather.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dikascode.openweather.R
import com.dikascode.openweather.ui.viewmodel.WeatherState
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences(stringResource(R.string.prefs), Context.MODE_PRIVATE)
    val savedCity = preferences.getString(stringResource(R.string.favorite_city), "")
    var city by remember { mutableStateOf(TextFieldValue(savedCity ?: "")) }
    val weatherState by viewModel.weatherState.collectAsState()
    var fetchTriggered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.weather_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
                label = { Text(stringResource(R.string.enter_city_name)) },
                modifier = Modifier.fillMaxWidth()
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
                shape = RoundedCornerShape(8.dp),
                enabled = weatherState !is WeatherState.Loading
            ) {
                Text(stringResource(R.string.get_weather))
            }

            when (weatherState) {
                is WeatherState.Loading -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(stringResource(R.string.loading), color = Color.White)
                }
                is WeatherState.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Error: ${(weatherState as WeatherState.Error).message}", color = Color.White)
                }
                is WeatherState.Empty -> {
                    // Handle empty state if needed
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
    }

    LaunchedEffect(weatherState, fetchTriggered) {
        if (fetchTriggered && weatherState is WeatherState.Error) {
            Toast.makeText(context, "Error: ${(weatherState as WeatherState.Error).message}", Toast.LENGTH_LONG).show()
            fetchTriggered = false
        }
    }
}
