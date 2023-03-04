package com.testapp.data.networking

import com.testapp.data.networking.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AppServices {
    @GET("forecast.json?&days=3&aqi=no&alerts=no")
    suspend fun getWeatherByQuery(@Query("q") location: String): WeatherDto
}