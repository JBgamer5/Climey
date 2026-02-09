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
 * A composable component that displays the visibility distance in kilometers.
 *
 * @param value The visibility value to be displayed.
 * @param modifier The [Modifier] to be applied to the component.
 */
@Composable
fun VisibilityComponent(
    value: String,
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
                    text = value.plus(stringResource(R.string.km)),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(R.string.visibility_title),
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
            VisibilityComponent(
                value = "35",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}