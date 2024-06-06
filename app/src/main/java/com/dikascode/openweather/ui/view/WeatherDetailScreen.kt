package com.dikascode.openweather.ui.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dikascode.openweather.R
import com.dikascode.openweather.ui.view.components.WeatherCard
import com.dikascode.openweather.ui.viewmodel.WeatherState
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(navController: NavController, viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val weatherState by viewModel.weatherState.collectAsState()

    var isFavorite by remember { mutableStateOf(preferences.getString("favorite_city", "") == (weatherState as? WeatherState.Success)?.weather?.name) }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.weather_details)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    if (weatherState is WeatherState.Success) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = stringResource(R.string.favorite),
                            tint = if (isFavorite) Color(0xFFFFA500) else Color.Gray,
                            modifier = Modifier
                                .size(44.dp)
                                .clickable {
                                    isFavorite = !isFavorite
                                    val cityName =
                                        (weatherState as WeatherState.Success).weather.name
                                    if (isFavorite) {
                                        preferences
                                            .edit()
                                            .putString("favorite_city", cityName)
                                            .apply()
                                        Toast
                                            .makeText(
                                                context,
                                                "$cityName has been saved as favorite",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    } else {
                                        preferences
                                            .edit()
                                            .remove("favorite_city")
                                            .apply()
                                        Toast
                                            .makeText(
                                                context,
                                                "$cityName has been removed from favorite",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    }
                                }
                                .padding(end = 16.dp)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (weatherState) {
                    is WeatherState.Loading -> {
                        Text("Loading...", style = MaterialTheme.typography.bodyLarge)
                    }
                    is WeatherState.Error -> {
                        Text("Error: ${(weatherState as WeatherState.Error).message}", style = MaterialTheme.typography.bodyLarge)
                    }
                    is WeatherState.Empty -> {
                       
                    }
                    is WeatherState.Success -> {
                        Log.d("weather state 2", Gson().toJson(weatherState))
                        WeatherCard(weather = (weatherState as WeatherState.Success).weather)
                    }
                }
            }
        }
    )
}
