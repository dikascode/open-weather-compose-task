package com.dikascode.openweather.data.repository

import com.dikascode.openweather.data.model.*
import com.dikascode.openweather.data.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertEquals

class WeatherRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        weatherRepository = WeatherRepository(apiService)
    }

    @Test
    fun `test getWeather success`() = runBlocking {
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
        `when`(apiService.getWeather("Abuja", "fakeApiKey", "metric")).thenReturn(weatherResponse)

        val result = weatherRepository.getWeather("Abuja", "fakeApiKey")

        assert(result is Result.Success)
        assertEquals(weatherResponse, (result as Result.Success).data)
    }

    @Test
    fun `test getWeather http error`() = runBlocking {
        val responseBody = ResponseBody.create(null, "Error")
        val httpException = HttpException(Response.error<WeatherResponse>(400, responseBody))
        `when`(apiService.getWeather("Abuja", "fakeApiKey", "metric")).thenThrow(httpException)

        val result = weatherRepository.getWeather("Abuja", "fakeApiKey")

        assert(result is Result.Error)
        assertEquals("HTTP error: 400", (result as Result.Error).message)
    }

    @Test
    fun `test getWeather network error`() = runBlocking {
        `when`(apiService.getWeather("Abuja", "fakeApiKey", "metric")).thenAnswer { throw IOException("Network error") }

        val result = weatherRepository.getWeather("Abuja", "fakeApiKey")

        assert(result is Result.Error)
        assertEquals("Network error", (result as Result.Error).message)
    }
}
