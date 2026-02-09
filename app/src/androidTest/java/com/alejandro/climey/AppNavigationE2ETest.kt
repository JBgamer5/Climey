package com.alejandro.climey

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.alejandro.climey.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

class AppNavigationE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun user_can_search_location_and_navigate_to_see_weather_detail_information() {
        //STEP 1 click on searchBar
        composeTestRule
            .onNodeWithTag("search_bar")
            .performClick()

        //STEP 2 type a location
        composeTestRule
            .onNodeWithTag("search_bar_input")
            .performTextInput("bu")

        //STEP 3 wait for the result and click on the result
        composeTestRule.waitUntil(2000) {
            composeTestRule
                .onAllNodesWithText("Buenos Aires, Argentina")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("Buenos Aires, Argentina")
            .performClick()

        //STEP 4 wait for the result and check if the weather information is displayed
        composeTestRule.waitUntil(5000) {
            composeTestRule
                .onAllNodesWithTag("current_weather_component")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule
            .onNodeWithTag("current_weather_component")
            .assertExists()
    }
}