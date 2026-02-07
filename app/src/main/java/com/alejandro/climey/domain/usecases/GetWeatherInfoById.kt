package com.alejandro.climey.domain.usecases

import com.alejandro.climey.domain.repository.WeatherRepository

class GetWeatherInfoById(private val repository: WeatherRepository) {
    suspend operator fun invoke(id: Int) = repository.getWeatherInfoById("id:$id")

}