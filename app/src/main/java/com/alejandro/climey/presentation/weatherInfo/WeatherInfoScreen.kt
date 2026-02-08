package com.alejandro.climey.presentation.weatherInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeatherInfoScreen(id: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "$id"
        )
    }
}