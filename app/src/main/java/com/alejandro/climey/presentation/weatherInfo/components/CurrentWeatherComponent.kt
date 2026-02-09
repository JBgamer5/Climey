package com.alejandro.climey.presentation.weatherInfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.climey.R
import com.alejandro.climey.core.ui.theme.ClimeyTheme


/**
 * Composable that displays the current weather information in a styled card.
 *
 * This component provides a visual summary of weather data, including the location name,
 * current temperature, descriptive condition, and the daily temperature range (min/max).
 *
 * @param modifier The [Modifier] to be applied to the outer layout.
 * @param city The name of the city or location.
 * @param temp The current temperature value (without unit).
 * @param tempMin The daily minimum temperature value (without unit).
 * @param tempMax The daily maximum temperature value (without unit).
 * @param condition A string describing the current weather (e.g., "Sunny", "Partly Cloudy").
 * @param maxLinesCondition The maximum number of lines allowed for the condition text before truncating.
 */
@Composable
fun CurrentWeatherComponent(
    modifier: Modifier = Modifier,
    city: String,
    temp: String,
    tempMin: String,
    tempMax: String,
    condition: String,
    maxLinesCondition: Int = 2
) {
    Box(modifier = modifier) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = city,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                Text(
                    text = temp.plus("°C"),
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                Text(
                    text = condition,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    maxLines = maxLinesCondition,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    Text(
                        text = stringResource(R.string.current_weather_component_temp_min),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(R.string.current_weather_component_temp_max),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = tempMin.plus("°C"),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        text = tempMax.plus("°C"),
                        style = MaterialTheme.typography.headlineLarge,
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
            CurrentWeatherComponent(
                city = "Buenos Aires",
                temp = "35",
                tempMin = "20",
                tempMax = "40",
                condition = "Sunny",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.Center)
            )
        }
    }
}