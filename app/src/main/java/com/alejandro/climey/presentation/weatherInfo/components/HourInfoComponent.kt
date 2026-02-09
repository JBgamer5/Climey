package com.alejandro.climey.presentation.weatherInfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.climey.core.ui.theme.ClimeyTheme

/**
 * A composable component that displays weather information for a specific hour.
 * It shows the time, the temperature in Celsius, and the weather condition description
 * within a styled card.
 *
 * @param hour The string representing the hour to be displayed.
 * @param temp The numeric value of the temperature as a string.
 * @param condition A description of the weather condition (e.g., "Sunny", "Cloudy").
 * @param modifier The [Modifier] to be applied to the component's layout.
 */
@Composable
fun HourInfoComponent(
    hour: String,
    temp: String,
    condition: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .defaultMinSize(
                        minHeight = 120.dp,
                        minWidth = 80.dp
                    )
            ) {
                Text(
                    text = hour,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = temp.plus("°C"),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = condition,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(85.dp)
                )
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
            HourInfoComponent(
                hour = "12",
                temp = "35",
                condition = "Sunny",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}