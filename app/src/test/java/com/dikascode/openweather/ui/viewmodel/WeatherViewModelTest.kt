package com.dikascode.openweather.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dikascode.openweather.data.model.*
import com.dikascode.openweather.data.repository.Result
import com.dikascode.openweather.data.repository.WeatherRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: WeatherRepository
    private lateinit var viewModel: WeatherViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = WeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `test fetchWeather success`() = runTest {
        val weatherResponse = WeatherResponse(
            coord = Coord(lon = -0.1257, lat = 51.5085),
            weather = listOf(Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")),
            base = "stations",
            main = Main(
                temp = 293.55,
                feels_like = 293.13,
                temp_min = 292.15,
                temp_max = 294.82,
                pressure = 1013,
                humidity = 53,
                sea_level = 1013,
                grnd_level = 1006
            ),
            visibility = 10000,
            wind = Wind(speed = 4.1, deg = 80, gust = 7.7),
            rain = null,
            clouds = Clouds(all = 0),
            dt = 1605182400,
            sys = Sys(type = 1, id = 1414, country = "GB", sunrise = 1605152613, sunset = 1605186202),
            timezone = 0,
            id = 2643743,
            name = "Abuja",
            cod = 200
        )
        val successResult = Result.Success(weatherResponse)

        coEvery { repository.getWeather("Abuja", "fakeApiKey") } returns successResult

        viewModel.fetchWeather("Abuja")

        assert(viewModel.weatherState.value is WeatherState.Success)
        assertEquals(weatherResponse, (viewModel.weatherState.value as WeatherState.Success).weather)
    }

    @Test
    fun `test fetchWeather error`() = runTest {
        val errorResult = Result.Error("HTTP error")

        coEvery { repository.getWeather("Abuja", "fakeApiKey") } returns errorResult

        viewModel.fetchWeather("Abuja")

        assert(viewModel.weatherState.value is WeatherState.Error)
        assertEquals("HTTP error", (viewModel.weatherState.value as WeatherState.Error).message)
    }
}
