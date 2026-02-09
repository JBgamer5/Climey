package com.alejandro.climey.presentation.weatherInfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alejandro.climey.R
import com.alejandro.climey.core.network.ApiError
import com.alejandro.climey.core.utils.ScreenSize
import com.alejandro.climey.core.utils.getDeviceSize
import com.alejandro.climey.core.utils.replaceFirstUpperCase
import com.alejandro.climey.domain.model.WeatherInfo
import com.alejandro.climey.presentation.weatherInfo.components.CloudComponent
import com.alejandro.climey.presentation.weatherInfo.components.CurrentWeatherComponent
import com.alejandro.climey.presentation.weatherInfo.components.DayInfoComponent
import com.alejandro.climey.presentation.weatherInfo.components.HourInfoComponent
import com.alejandro.climey.presentation.weatherInfo.components.HumidityComponent
import com.alejandro.climey.presentation.weatherInfo.components.ThermalSensationComponent
import com.alejandro.climey.presentation.weatherInfo.components.UvComponent
import com.alejandro.climey.presentation.weatherInfo.components.VisibilityComponent
import com.alejandro.climey.presentation.weatherInfo.components.WindComponent
import org.koin.compose.viewmodel.koinViewModel
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeatherInfoScreen(
    id: Int,
    navController: NavController,
    viewModel: WeatherInfoViewModel = koinViewModel<WeatherInfoViewModel>()
) {
    val infoHasLoadedOnce = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    val screenSize = getDeviceSize()

    val stateCombine = (state to screenSize)

    LaunchedEffect(Unit) {
        if (!infoHasLoadedOnce.value) {
            viewModel.loadInformation(id)
            infoHasLoadedOnce.value = true
        }
    }

    AnimatedContent(stateCombine) { (s, size) ->
        if (s.error != null) {
            ErrorContent(
                error = ApiError.NetworkError(),
                onRetry = {
                    viewModel.loadInformation(id)
                }
            )
        }

        if (s.data != null) {
            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { viewModel.loadInformation(id) }
            ) {
                Content(
                    data = s.data,
                    size = size,
                    navController = navController
                )
            }
        }

        if (s.isLoading) {
            LoadingContent()
        }
    }
}

@Composable
private fun Content(
    data: WeatherInfo,
    size: ScreenSize,
    navController: NavController
) {
    if (size == ScreenSize.Compact) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                CurrentWeatherContent(
                    data = data,
                    isCompactLandscape = false,
                    showSearch = true,
                    onClickSearch = {
                        navController.popBackStack()
                    }
                )
            }
            item {
                ExtraWeatherInfoContent(
                    data = data,
                    isCompactLandscape = false
                )
            }
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CurrentWeatherContent(
                data = data,
                isCompactLandscape = size == ScreenSize.CompactLandscape,
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    ExtraWeatherInfoContent(
                        data = data,
                        isCompactLandscape = size == ScreenSize.CompactLandscape,
                        showSearch = true,
                        onClickSearch = {
                            navController.popBackStack()
                        }
                    )
                }
            }

        }
    }
}

@Composable
private fun ExtraWeatherInfoContent(
    data: WeatherInfo,
    isCompactLandscape: Boolean,
    showSearch: Boolean = false,
    onClickSearch: () -> Unit = {}
) {
    val hourDayInfo = data.forecast.forecastDay.first().hours
    val aspectRatio = 3f / 4f
    val currentLocale = Locale.getDefault()

    FlowRow(
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        if (showSearch) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = onClickSearch,
                    modifier = Modifier
                        .padding(top = 20.dp, end = 20.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search button",
                        modifier = Modifier
                            .size(70.dp)
                    )
                }
            }
        }
        if (isCompactLandscape) {
            Text(
                text = stringResource(R.string.weather_info_forecast_hour),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                modifier = Modifier
                    .padding(bottom = 15.dp)
            ) {
                items(hourDayInfo) {
                    HourInfoComponent(
                        hour = it.time.hour.toString().padStart(2, '0'),
                        temp = it.tempC.roundToInt().toString(),
                        condition = it.condition.text
                    )
                }
            }
        }

        ThermalSensationComponent(
            temp = data.current.feelsLikeC.roundToInt().toString(),
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(aspectRatio)
        )

        WindComponent(
            value = data.current.windKph.roundToInt().toString(),
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(aspectRatio)
        )

        CloudComponent(
            percentage = data.current.cloud.toString(),
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(aspectRatio)
        )

        UvComponent(
            value = data.current.uv,
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(aspectRatio)
        )

        HumidityComponent(
            percentage = data.current.humidity.roundToInt().toString(),
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(aspectRatio)
        )

        VisibilityComponent(
            value = data.current.visKm.roundToInt().toString(),
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(aspectRatio)
        )

        Text(
            text = stringResource(R.string.weather_info_forecast_next_days),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 30.dp)
        )

        data.forecast.forecastDay.take(3).forEachIndexed { index, forecastDay ->
            val dayOfWeek = if (index == 0) {
                stringResource(R.string.weather_info_forecast_today)
            } else {
                forecastDay.date.dayOfWeek
                    .getDisplayName(TextStyle.FULL, currentLocale)
                    .replaceFirstUpperCase()
            }

            DayInfoComponent(
                iconUrl = forecastDay.day.condition.iconUrl,
                tempAvg = forecastDay.day.avgTempC.roundToInt().toString(),
                condition = forecastDay.day.condition.text,
                dayOfWeek = dayOfWeek,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
        }

    }

}

@Composable
private fun CurrentWeatherContent(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
    isCompactLandscape: Boolean,
    showSearch: Boolean = false,
    onClickSearch: () -> Unit = {}
) {
    val dayInfo = data.forecast.forecastDay.first().day
    val hourDayInfo = data.forecast.forecastDay.first().hours

    Column(modifier = modifier) {
        if (showSearch) {
            IconButton(
                onClick = onClickSearch,
                modifier = Modifier
                    .padding(top = 20.dp, end = 20.dp)
                    .align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search button",
                    modifier = Modifier
                        .size(70.dp)
                )
            }
        }

        CurrentWeatherComponent(
            city = data.location.name,
            temp = data.current.tempC.roundToInt().toString(),
            tempMin = dayInfo.minTempC.roundToInt().toString(),
            tempMax = dayInfo.maxTempC.roundToInt().toString(),
            condition = data.current.condition.text,
            maxLinesCondition = if (isCompactLandscape) 1 else 2,
            modifier = Modifier
                .testTag("current_weather_component")
        )
        if (!isCompactLandscape) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.weather_info_forecast_hour),
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 10.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(hourDayInfo) {
                    HourInfoComponent(
                        hour = it.time.hour.toString().padStart(2, '0'),
                        temp = it.tempC.roundToInt().toString(),
                        condition = it.condition.text
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    error: Throwable,
    onRetry: () -> Unit
) {
    val errorMessage = when (error) {
        is ApiError.NetworkError -> stringResource(R.string.search_error_no_network)
        is ApiError.SerializationError -> stringResource(R.string.search_error_serialization)
        is ApiError.InternalServerError -> stringResource(
            R.string.search_error_server,
            error.errorCode
        )

        else -> stringResource(R.string.search_error_unknown)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.error_animation)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onRetry
        ) {
            Text(stringResource(R.string.weather_info_error_button_retry))
        }

    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loading_animation)
        )

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            speed = 3f,
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp)
        )
    }
}