package com.alejandro.climey.domain.usecases

import com.alejandro.climey.core.network.ApiError
import com.alejandro.climey.domain.model.Location
import com.alejandro.climey.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SearchLocationTest {

    private val repository = mockk<WeatherRepository>()
    private val useCase = SearchLocation(repository)

    @Test
    fun `invoke result success if repository return data`() = runTest {
        //GIVEN
        val city = "Bucaramanga"
        val mockLocationInfo = listOf(mockk<Location>(relaxed = true))

        coEvery { repository.searchCity(cityName = city) } returns Result.success(mockLocationInfo)

        //WHEN
        val result = useCase(city)

        //THEN
        assert(result.isSuccess)
        assertEquals(mockLocationInfo, result.getOrNull())
    }

    @Test
    fun `invoke result fail if repository return error`() = runTest {
        //GIVEN
        val city = "Bucaramanga"
        val error = ApiError.InternalServerError(404)

        coEvery { repository.searchCity(cityName = city) } returns Result.failure(error)

        //WHEN
        val result = useCase(city)

        //THEN
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}