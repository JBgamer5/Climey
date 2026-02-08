package com.alejandro.climey.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data object Search : Screens()

    @Serializable
    data class WeatherInformation(val locationId: Int) : Screens()
}