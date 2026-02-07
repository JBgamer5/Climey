package com.alejandro.climey.data.repository

import com.alejandro.climey.core.network.safeApiCall
import com.alejandro.climey.data.remote.WeatherRemoteDataSource
import com.alejandro.climey.data.remote.model.toCurrentWeatherInfo
import com.alejandro.climey.data.remote.model.toLocation
import com.alejandro.climey.domain.model.Location
import com.alejandro.climey.domain.model.WeatherInfo
import com.alejandro.climey.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    private val daysExtras = 3

    override suspend fun searchCity(cityName: String): Result<List<Location>> = safeApiCall(
        call = { remoteDataSource.searchCity(cityName) },
        transform = { result ->
            result.map { it.toLocation() }
        }
    )

    override suspend fun getWeatherInfoById(id: String): Result<WeatherInfo> = safeApiCall(
        call = { remoteDataSource.getWeatherInfoById(id, daysExtras) },
        transform = { it.toCurrentWeatherInfo() }
    )
}