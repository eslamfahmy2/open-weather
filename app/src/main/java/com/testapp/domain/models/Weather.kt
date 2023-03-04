package com.testapp.domain.models

import android.text.format.DateUtils
import com.testapp.R
import com.testapp.data.networking.dto.ForecastDto
import com.testapp.data.networking.dto.WeatherDto
import com.testapp.utils.ApplicationContextSingleton
import java.text.SimpleDateFormat
import java.util.*

data class Weather(
    val name: String?,
    val localtime: String?,
    val cloud: Int?,
    val icon: String?,
    val text: String?,
    val humidity: Int?,
    val temp_f: Double?,
    val wind_mph: Double?,
    val forecast: List<Forecast>?
)

data class Forecast(
    val date: String?,
    val icon: String?,
    val maxtemp_f: Double?,
    val mintemp_f: Double?,
)


