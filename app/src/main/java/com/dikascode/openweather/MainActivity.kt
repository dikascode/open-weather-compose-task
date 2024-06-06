package com.dikascode.openweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dikascode.openweather.ui.theme.OpenWeatherComposeTheme
import com.dikascode.openweather.ui.view.HomeScreen
import com.dikascode.openweather.ui.view.SplashScreen
import com.dikascode.openweather.ui.view.WeatherDetailScreen
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenWeatherComposeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") { SplashScreen(navController) }
                        composable("home") { HomeScreen(navController, viewModel) }
                        composable("weatherDetail") { WeatherDetailScreen(navController, viewModel) }
                    }
                }
            }
        }
    }
}
