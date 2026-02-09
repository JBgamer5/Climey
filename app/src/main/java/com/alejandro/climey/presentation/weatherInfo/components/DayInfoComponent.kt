package com.alejandro.climey.presentation.weatherInfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alejandro.climey.core.ui.theme.ClimeyTheme

/**
 * A composable component that displays a summary of weather information for a specific day.
 *
 * It shows the day of the week, a weather icon, the weather condition description,
 * and the average temperature.
 *
 * @param iconUrl The URL of the icon representing the weather condition.
 * @param tempAvg The average temperature value to be displayed.
 * @param condition A brief description of the weather condition (e.g., "Sunny", "Cloudy").
 * @param dayOfWeek The name of the day of the week.
 * @param modifier The [Modifier] to be applied to the component.
 */
@Composable
fun DayInfoComponent(
    iconUrl: String,
    tempAvg: String,
    condition: String,
    dayOfWeek: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 20.dp)
                    .heightIn(min = 120.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = dayOfWeek,
                        style = MaterialTheme.typography.titleMedium
                    )
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = "weather condition",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = condition,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(.7f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = tempAvg.plus("°C"),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    ClimeyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DayInfoComponent(
                iconUrl = "http://example.png",
                tempAvg = "30",
                dayOfWeek = "Monday",
                condition = "Sunny",
                modifier = Modifier

                    .align(Alignment.Center)
            )
        }
    }
}