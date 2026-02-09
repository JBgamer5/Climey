package com.alejandro.climey.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alejandro.climey.presentation.search.SearchScreen
import com.alejandro.climey.presentation.weatherInfo.WeatherInfoScreen

@Composable
fun MainNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screens.Search,
        modifier = Modifier
            .padding(innerPadding)
    ) {
        composable<Screens.Search> {
            SearchScreen(navController)
        }

        composable<Screens.WeatherInformation> {
            val id = it.toRoute<Screens.WeatherInformation>().locationId

            WeatherInfoScreen(id, navController)
        }
    }
}