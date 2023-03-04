package com.testapp.data.networking.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecastday")
    val forecastday: List<ForecastdayDto>?
)

data class ForecastdayDto(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: DayDto?,
)

data class DayDto(
    @SerializedName("condition")
    val condition: ConditionDto?,
    @SerializedName("maxtemp_f")
    val maxtemp_f: Double?,
    @SerializedName("mintemp_f")
    val mintemp_f: Double?,
)