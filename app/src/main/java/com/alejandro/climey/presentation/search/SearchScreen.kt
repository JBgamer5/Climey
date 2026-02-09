package com.alejandro.climey.presentation.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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
import com.alejandro.climey.presentation.navigation.Screens
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<SearchViewModel>()
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchState = rememberSearchBarState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val screenSize = getDeviceSize()
    var expandedResult by remember { mutableStateOf(false) }

    AnimatedContent(
        screenSize,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) { size ->
        if (size == ScreenSize.CompactLandscape) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SearchContent(
                    viewModel = viewModel,
                    searchQuery = searchQuery,
                    searchState = searchState,
                    expandedResult = expandedResult,
                    onExpandedChange = { expandedResult = it && searchQuery.isNotBlank() },
                    keyboardController = keyboardController,
                    enableScroll = true
                )
                AnimatedVisibility(expandedResult || searchQuery.isNotBlank()) {
                    Row {
                        Spacer(modifier = Modifier.width(20.dp))
                        ResultContent(
                            state = state,
                            enableScroll = true,
                            onClick = { id ->
                                id?.let {
                                    keyboardController?.hide()
                                    navController.navigate(Screens.WeatherInformation(it))
                                }
                            }
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .fillParentMaxHeight(
                                if (screenSize == ScreenSize.Compact) 0.17f
                                else 0.1f
                            )
                    )
                    SearchContent(
                        viewModel = viewModel,
                        searchQuery = searchQuery,
                        searchState = searchState,
                        expandedResult = expandedResult,
                        onExpandedChange = { expandedResult = it },
                        keyboardController = keyboardController
                    )
                }

                item {
                    AnimatedVisibility(
                        expandedResult,
                        modifier = Modifier
                            .sizeIn(
                                minWidth = 360.dp,
                                maxWidth = 720.dp,
                            )
                    ) {
                        ResultContent(
                            state = state,
                            onClick = { id ->
                                id?.let {
                                    keyboardController?.hide()
                                    navController.navigate(Screens.WeatherInformation(it))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchContent(
    viewModel: SearchViewModel,
    keyboardController: SoftwareKeyboardController?,
    searchQuery: String,
    searchState: SearchBarState,
    expandedResult: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    enableScroll: Boolean = false
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.searching_animation)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = if (enableScroll)
            Modifier.verticalScroll(rememberScrollState())
        else Modifier

    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(200.dp)
        )

        SearchBar(
            state = searchState,
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange,
                    onSearch = {
                        viewModel.onSearchQueryChange(it)
                        keyboardController?.hide()
                    },
                    expanded = expandedResult,
                    onExpandedChange = onExpandedChange,
                    placeholder = {
                        Text(text = stringResource(R.string.search_place_holder))
                    }
                )
            }
        )
    }
}

@Composable
private fun ResultContent(
    state: SearchState,
    enableScroll: Boolean = false,
    onClick: (id: Int?) -> Unit
) {
    if (state.error != null) {
        val errorMessage = when (state.error) {
            is ApiError.NetworkError -> stringResource(R.string.search_error_no_network)
            is ApiError.SerializationError -> stringResource(R.string.search_error_serialization)
            is ApiError.InternalServerError -> stringResource(
                R.string.search_error_server,
                state.error.errorCode
            )

            else -> stringResource(R.string.search_error_unknown)
        }

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .sizeIn(
                    minWidth = 360.dp,
                    maxWidth = 720.dp,
                )
        )
    } else {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if (enableScroll)
                    Modifier.verticalScroll(rememberScrollState())
                else Modifier

            ) {
                state.results.forEach {
                    Text(
                        text = "${it.name}, ${it.country}",
                        modifier = Modifier
                            .clickable {
                                onClick(it.id)
                            }
                            .sizeIn(
                                minWidth = 360.dp,
                                maxWidth = 720.dp,
                            )
                            .minimumInteractiveComponentSize()
                            .padding(start = 15.dp)
                    )
                }
            }
        }
    }
}