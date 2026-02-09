package com.alejandro.climey.presentation.weatherInfo

import com.alejandro.climey.core.network.ApiError
import com.alejandro.climey.domain.model.WeatherInfo
import com.alejandro.climey.domain.usecases.GetWeatherInfoById
import com.alejandro.climey.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class WeatherInfoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getWeatherInfoByIdUseCase = mockk<GetWeatherInfoById>()
    private lateinit var viewModel: WeatherInfoViewModel

    @Before
    fun setup() {
        viewModel = WeatherInfoViewModel(getWeatherInfoByIdUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange updates state to success if searchLocation succeeds`() = runTest {
        //GIVEN
        val id = 1234
        val dataMock = mockk<WeatherInfo>(relaxed = true)

        coEvery { getWeatherInfoByIdUseCase(id) } returns Result.success(dataMock)

        //WHEN
        viewModel.loadInformation(id)

        //THEN
        val state = viewModel.state.value

        assertEquals(dataMock, state.data)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange updates state to failure if searchLocation fails`() = runTest {
        //GIVEN
        val id = 1234
        val error = ApiError.NetworkError()

        coEvery { getWeatherInfoByIdUseCase(id) } returns Result.failure(error)

        //WHEN
        viewModel.loadInformation(id)

        //THEN
        val state = viewModel.state.value

        assertNull(state.data)
        assertFalse(state.isLoading)
        assertEquals(error, state.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange updates state to loading before returning results`() = runTest {
        //GIVEN
        val id = 1234

        coEvery { getWeatherInfoByIdUseCase(id) } coAnswers {
            delay(1000L)
            Result.success(mockk())
        }

        //WHEN
        viewModel.loadInformation(id)

        //THEN
        val state = viewModel.state.value

        assertNull(state.data)
        assertTrue(state.isLoading)
        assertNull(state.error)
    }

}