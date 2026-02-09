package com.alejandro.climey.presentation.search

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.alejandro.climey.domain.model.Location
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun result_list_shows_items() {
        //GIVEN
        lateinit var navController: NavController
        val locations = listOf(Location(1, "Socorro", "Santander", country = "Colombia"))
        val vm = mockk<SearchViewModel>(relaxed = true)
        val stateFlow = MutableStateFlow(SearchState(results = locations))

        every { vm.state } returns stateFlow.asStateFlow()
        every { vm.searchQuery } returns MutableStateFlow("")

        //WHEN
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SearchScreen(navController, vm)
        }

        //THEN
        composeTestRule.onNodeWithTag("search_bar").assertIsDisplayed().performClick()

        composeTestRule.onNodeWithText("Socorro, Colombia").assertIsDisplayed()
    }
}