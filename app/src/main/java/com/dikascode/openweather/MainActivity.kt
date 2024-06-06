package com.dikascode.openweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dikascode.openweather.ui.theme.OpenWeatherComposeTheme
import com.dikascode.openweather.ui.view.HomeScreen
import com.dikascode.openweather.ui.view.SplashScreen
import com.dikascode.openweather.ui.view.WeatherDetailScreen
import com.dikascode.openweather.ui.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenWeatherComposeTheme {
                val navController = rememberNavController()

                //creating shared viewmodel at a higher navigation hierarchy
                val viewModel: WeatherViewModel = hiltViewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = getString(R.string.splash)) {
                        composable(getString(R.string.splash)) { SplashScreen(navController) }
                        composable(getString(R.string.home)) { HomeScreen(navController, viewModel) }
                        composable(getString(R.string.weatherdetail)) { WeatherDetailScreen(navController, viewModel) }
                    }
                }
            }
        }
    }
}
