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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alejandro.climey.R
import com.alejandro.climey.core.ui.theme.ClimeyTheme

/**
 * A composable component that displays the humidity level using an animated icon and a percentage text.
 *
 * @param percentage The humidity value to be displayed, typically as a numeric string.
 * @param modifier The [Modifier] to be applied to the component's layout.
 */
@Composable
fun HumidityComponent(
    percentage: String,
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
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.humidity_animation))

                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    reverseOnRepeat = true,
                    speed = .6f,
                    modifier = Modifier
                        .size(90.dp)
                )

                Text(
                    text = percentage.plus("%"),
                    style = MaterialTheme.typography.displaySmall
                )

                Text(
                    text = stringResource(R.string.humidity_title),
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
            HumidityComponent(
                percentage = "70",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}