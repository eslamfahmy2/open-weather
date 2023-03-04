package com.testapp.data.networking.dto

import com.google.gson.annotations.SerializedName


data class WeatherDto(
    @SerializedName("location")
    val location: LocationDto?,
    @SerializedName("current")
    val current: CurrentDto,
    @SerializedName("forecast")
    val forecast: ForecastDto?,
)

data class LocationDto(
    @SerializedName("country")
    val country: String?,
    @SerializedName("localtime")
    val localtime: String?,
    @SerializedName("name")
    val name: String?,
)

data class CurrentDto(
    @SerializedName("cloud")
    val cloud: Int?,
    @SerializedName("condition")
    val condition: ConditionDto?,
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("temp_f")
    val temp_f: Double?,
    @SerializedName("wind_mph")
    val wind_mph: Double?
)

data class ConditionDto(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("text")
    val text: String?
)
