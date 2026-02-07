package com.alejandro.climey.domain.repository

import com.alejandro.climey.domain.model.Location
import com.alejandro.climey.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun searchCity(cityName: String): Result<List<Location>>

    suspend fun getWeatherInfoById(id: String): Result<WeatherInfo>
}