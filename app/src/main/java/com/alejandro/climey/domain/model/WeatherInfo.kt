package com.alejandro.climey.domain.model

import com.alejandro.climey.data.remote.model.LocationDto
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherInfo(
    val location: LocationDto,
    val current: CurrentWeatherInfo,
    val forecast: Forecast
)

data class CurrentWeatherInfo(
    val tempC: Double,
    val condition: Condition,
    val windKph: Double,
    val humidity: Double,
    val cloud: Int,
    val feelsLikeC: Double,
    val visKm: Double,
    val uv: Double
)

data class Condition(
    val text: String,
    val iconUrl: String
)

data class Forecast(
    val forecastDay: List<ForecastDay>
)

data class ForecastDay(
    val date: LocalDate,
    val day: DayInfo,
    val hours: List<HourInfo>
)

data class DayInfo(
    val maxTempC: Double,
    val minTempC: Double,
    val avgTempC: Double,
    val condition: Condition
)

data class HourInfo(
    val time: LocalDateTime,
    val condition: Condition,
    val tempC: Double
)
