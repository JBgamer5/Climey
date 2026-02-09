package com.alejandro.climey.presentation.weatherInfo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.climey.R
import com.alejandro.climey.core.ui.theme.ClimeyTheme
import kotlin.math.roundToInt

/**
 * A composable function that displays a visual representation of the Ultraviolet (UV) index.
 *
 * It features a colored progress bar indicating the UV level (from low to extreme)
 * with a dynamic indicator showing the current value and a textual description
 * of the risk level.
 *
 * @param value The UV index value, typically ranging from 0 to 11+.
 * @param modifier The [Modifier] to be applied to the component.
 */
@Composable
fun UvComponent(
    value: Double,
    modifier: Modifier = Modifier
) {
    val percentage = value.div(11).toFloat()

    val gradientColor = Brush.linearGradient(
        0.0f to Color(0xFF1F9E49),
        0.27f to Color(0xFFFDCF3B),
        0.54f to Color(0xFFF18016),
        0.72f to Color(0xFFE54D2E),
        1f to Color(0xFF8A64A1)
    )

    val indicatorColor = when (value.roundToInt()) {
        in 0..2 -> Color(0xFF1F9E49)
        in 3..5 -> Color(0xFFFDCF3B)
        in 6..7 -> Color(0xFFF18016)
        in 8..10 -> Color(0xFFE54D2E)
        else -> Color(0xFF8A64A1)
    }

    val description = when (value.roundToInt()) {
        in 0..2 -> stringResource(R.string.uv_low)
        in 3..5 -> stringResource(R.string.uv_medium)
        in 6..7 -> stringResource(R.string.uv_high)
        in 8..10 -> stringResource(R.string.uv_very_high)
        else -> stringResource(R.string.uv_extreme)
    }

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
                @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
                BoxWithConstraints {
                    val offsetX = maxWidth.minus(30.dp) * percentage
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(
                                gradientColor,
                                shape = CircleShape
                            )
                            .align(Alignment.Center)
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .offset(offsetX)
                            .size(30.dp)
                            .background(
                                indicatorColor,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = value.roundToInt().toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                Text(
                    text = description,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.uv_title),
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
            UvComponent(
                value = 1.0,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}