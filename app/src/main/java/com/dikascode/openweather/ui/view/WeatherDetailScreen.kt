package com.dikascode.openweather.ui.view

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dikascode.openweather.ui.view.components.WeatherCard
import com.dikascode.openweather.ui.viewmodel.WeatherState
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(navController: NavController, viewModel: WeatherViewModel) {
    val weatherState by viewModel.weatherState.collectAsState()

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
//                        CircularProgressIndicator()
                        Text("Loading...", style = MaterialTheme.typography.bodyLarge)
                    }
                    is WeatherState.Error -> {
                        Text("Error: ${(weatherState as WeatherState.Error).message}", style = MaterialTheme.typography.bodyLarge)
                    }
                    is WeatherState.Empty -> {
//                        Text("No data available", style = MaterialTheme.typography.bodyLarge)
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
