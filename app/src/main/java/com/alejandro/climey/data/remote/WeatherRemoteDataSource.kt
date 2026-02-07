package com.alejandro.climey.data.remote

import com.alejandro.climey.data.remote.model.LocationDto
import com.alejandro.climey.data.remote.model.WeatherInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteDataSource {

    @GET("search.json")
    suspend fun searchCity(@Query("q") value: String): Response<List<LocationDto>>

    @GET("forecast.json")
    suspend fun getWeatherInfoById(
        @Query("q") id: String,
        @Query("days") days: Int
    ): Response<WeatherInfoDto>
}