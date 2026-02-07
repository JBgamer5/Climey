package com.alejandro.climey.domain.usecases

import com.alejandro.climey.domain.repository.WeatherRepository

class SearchLocation(private val repository: WeatherRepository) {
    suspend operator fun invoke(cityName: String) = repository.searchCity(cityName)
}