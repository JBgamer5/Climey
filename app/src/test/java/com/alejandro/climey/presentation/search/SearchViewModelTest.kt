package com.alejandro.climey.presentation.search

import com.alejandro.climey.core.network.ApiError
import com.alejandro.climey.domain.model.Location
import com.alejandro.climey.domain.usecases.SearchLocation
import com.alejandro.climey.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchLocationUseCase = mockk<SearchLocation>()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchLocationUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange updates state to success if searchLocation succeeds`() = runTest {
        //GIVEN
        val cityName = "Socorro"
        val dataMock = listOf(mockk<Location>(relaxed = true))

        coEvery { searchLocationUseCase(cityName) } returns Result.success(dataMock)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }

        //WHEN
        viewModel.onSearchQueryChange(cityName)

        advanceTimeBy(301L)

        runCurrent()

        //THEN
        val state = viewModel.state.value

        assertEquals(dataMock, state.results)
        assertFalse(state.isLoading)
        assertNull(state.error)

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange updates state to failure if searchLocation fails`() = runTest {
        //GIVEN
        val cityName = "Socorro"
        val error = ApiError.NetworkError()

        coEvery { searchLocationUseCase(cityName) } returns Result.failure(error)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }

        //WHEN
        viewModel.onSearchQueryChange(cityName)

        advanceTimeBy(301L)

        runCurrent()

        //THEN
        val state = viewModel.state.value

        assertEquals(emptyList<Location>(), state.results)
        assertFalse(state.isLoading)
        assertEquals(error, state.error)

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange updates state to loading before returning results`() = runTest {
        //GIVEN
        val cityName = "Socorro"

        coEvery { searchLocationUseCase(cityName) } coAnswers {
            delay(1000L)
            Result.success(emptyList())
        }

        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }

        //WHEN
        viewModel.onSearchQueryChange(cityName)

        advanceTimeBy(301L)

        runCurrent()

        //THEN
        val state = viewModel.state.value

        assertEquals(emptyList<Location>(), state.results)
        assertTrue(state.isLoading)
        assertNull(state.error)

        collectJob.cancel()
    }

}