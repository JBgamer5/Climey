package com.alejandro.climey.data.remote.model

import com.alejandro.climey.core.utils.toLocalDate
import com.alejandro.climey.core.utils.toLocalDateTime
import com.alejandro.climey.domain.model.Condition
import com.alejandro.climey.domain.model.CurrentWeatherInfo
import com.alejandro.climey.domain.model.DayInfo
import com.alejandro.climey.domain.model.Forecast
import com.alejandro.climey.domain.model.ForecastDay
import com.alejandro.climey.domain.model.HourInfo
import com.alejandro.climey.domain.model.WeatherInfo
import com.google.gson.annotations.SerializedName

data class WeatherInfoDto(
    val location: LocationDto,
    val current: CurrentWeatherInfoDto,
    val forecast: ForecastDto
)

data class CurrentWeatherInfoDto(
    @SerializedName("temp_c")
    val tempC: Double,
    val condition: ConditionDto,
    @SerializedName("wind_kph")
    val windKph: Double,
    val humidity: Double,
    val cloud: Int,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    val uv: Double
)

data class ConditionDto(
    val text: String,
    @SerializedName("icon")
    val iconUrl: String
)

data class ForecastDto(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayDto>
)

data class ForecastDayDto(
    val date: String,
    val day: DayInfoDto,
    val hour: List<HourInfoDto>
)

data class DayInfoDto(
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double,
    @SerializedName("avgtemp_c")
    val avgTempC: Double,
    val condition: ConditionDto
)

data class HourInfoDto(
    val time: String,
    val condition: ConditionDto,
    @SerializedName("temp_c")
    val tempC: Double
)

fun ConditionDto.toCondition(): Condition {
    return Condition(
        text = text,
        iconUrl = iconUrl.replace("//", "https://")
    )
}

fun HourInfoDto.toHourInfo(): HourInfo {
    return HourInfo(
        time = time.toLocalDateTime(),
        condition = condition.toCondition(),
        tempC = tempC
    )
}

fun DayInfoDto.toDayInfo(): DayInfo {
    return DayInfo(
        maxTempC = maxTempC,
        minTempC = minTempC,
        avgTempC = avgTempC,
        condition = condition.toCondition()
    )
}

fun ForecastDayDto.toForecastDay(): ForecastDay {
    return ForecastDay(
        date = date.toLocalDate(),
        day = day.toDayInfo(),
        hour = hour.map { it.toHourInfo() }
    )
}

fun ForecastDto.toForecastDay(): Forecast {
    return Forecast(
        forecastDay = forecastDay.map { it.toForecastDay() }
    )
}

fun CurrentWeatherInfoDto.toCurrentWeatherInfo(): CurrentWeatherInfo {
    return CurrentWeatherInfo(
        tempC = tempC,
        condition = condition.toCondition(),
        windKph = windKph,
        humidity = humidity,
        cloud = cloud,
        feelsLikeC = feelsLikeC,
        visKm = visKm,
        uv = uv
    )
}

fun WeatherInfoDto.toCurrentWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        location = location,
        current = current.toCurrentWeatherInfo(),
        forecast = forecast.toForecastDay()
    )
}
