package com.alejandro.climey.presentation.weatherInfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.climey.R
import com.alejandro.climey.core.ui.theme.ClimeyTheme

/**
 * A composable component that displays the thermal sensation (feels-like temperature).
 *
 * It shows the temperature value appended with the Celsius unit and a descriptive title,
 * enclosed within a stylized [Surface].
 *
 * @param temp The temperature value to be displayed as a string.
 * @param modifier The [Modifier] to be applied to the component's layout.
 */
@Composable
fun ThermalSensationComponent(
    temp: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = temp.plus("°C"),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(R.string.thermal_sensation_title),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
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
            ThermalSensationComponent(
                temp = "35",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}