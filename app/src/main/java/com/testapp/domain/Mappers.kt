package com.testapp.domain

import com.testapp.data.networking.dto.ForecastDto
import com.testapp.data.networking.dto.WeatherDto
import com.testapp.domain.models.Forecast
import com.testapp.domain.models.Weather



fun WeatherDto.mapToDomainModel(): Weather {
    return Weather(
        name = this.location?.name,
        localtime = this.location?.localtime,
        cloud = this.current.cloud,
        humidity = this.current.humidity,
        temp_f = this.current.temp_f,
        wind_mph = this.current.wind_mph,
        text = this.current.condition?.text,
        icon = this.current.condition?.icon,
        forecast = this.forecast?.mapToDomain()
    )
}

fun ForecastDto.mapToDomain(): List<Forecast>? {
    return this.forecastday?.map {
        Forecast(
            date = it.date,
            icon = it.day?.condition?.icon,
            maxtemp_f = it.day?.maxtemp_f,
            mintemp_f = it.day?.mintemp_f
        )
    }
}