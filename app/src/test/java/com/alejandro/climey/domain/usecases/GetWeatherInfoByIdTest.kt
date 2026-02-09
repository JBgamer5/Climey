package com.alejandro.climey.domain.usecases

import com.alejandro.climey.core.network.ApiError
import com.alejandro.climey.domain.model.WeatherInfo
import com.alejandro.climey.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test


class GetWeatherInfoByIdTest {

    private val repository = mockk<WeatherRepository>()
    private val useCase = GetWeatherInfoById(repository)

    @Test
    fun `invoke result success if repository return data`() = runTest {
        //GIVEN
        val id = 1234
        val mockWeatherInfo = mockk<WeatherInfo>(relaxed = true)

        coEvery { repository.getWeatherInfoById("id:$id") } returns Result.success(mockWeatherInfo)

        //WHEN
        val result = useCase(id)

        //THEN
        assert(result.isSuccess)
        assertEquals(mockWeatherInfo, result.getOrNull())
    }

    @Test
    fun `invoke result fail if repository return error`() = runTest {
        //GIVEN
        val id = 1234
        val error = ApiError.SerializationError()

        coEvery { repository.getWeatherInfoById("id:$id") } returns Result.failure(error)

        //WHEN
        val result = useCase(id)

        //THEN
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}