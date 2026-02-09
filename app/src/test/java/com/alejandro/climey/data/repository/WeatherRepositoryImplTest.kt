package com.alejandro.climey.data.repository

import com.alejandro.climey.core.network.ApiError
import com.alejandro.climey.data.remote.WeatherRemoteDataSource
import com.alejandro.climey.data.remote.model.LocationDto
import com.alejandro.climey.data.remote.model.WeatherInfoDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class WeatherRepositoryImplTest {

    private val remoteDataSource = mockk<WeatherRemoteDataSource>()
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        repository = WeatherRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `searchCity return success when api call is successful and return data`() = runTest {
        //GIVEN
        val cityName = "Buenos Aires"
        val mockLocationList = listOf(mockk<LocationDto>(relaxed = true))
        val mockResponse = Response.success(mockLocationList)

        coEvery { remoteDataSource.searchCity(cityName) } returns mockResponse

        //WHEN
        val result = repository.searchCity(cityName)

        //THEN
        assertTrue(result.isSuccess)

        val expectedData = result.getOrNull()
        assertEquals(1, expectedData?.size)
    }

    @Test
    fun `searchCity return failure when api call fails and only call once`() = runTest {
        //GIVEN
        val cityName = "Buenos Aires"
        val error = ApiError.NetworkError()

        coEvery { remoteDataSource.searchCity(cityName) } throws error

        //WHEN
        val result = repository.searchCity(cityName)

        //THEN
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())

        coVerify(exactly = 1) { remoteDataSource.searchCity(cityName) }
    }

    @Test
    fun `getWeatherInfoById return success when api call is successful and return data`() =
        runTest {
            //GIVEN
            val idCity = "id:1234"
            val queryDays = 3
            val mockDto = mockk<WeatherInfoDto>(relaxed = true)
            val mockResponse = Response.success(mockDto)

            coEvery { remoteDataSource.getWeatherInfoById(idCity, queryDays) } returns mockResponse

            //WHEN
            val result = repository.getWeatherInfoById(idCity)

            //THEN
            assertTrue(result.isSuccess)

            val expectedData = result.getOrNull()
            assert(expectedData != null)
        }

    @Test
    fun `getWeatherInfoById return failure when api call fails and only call once`() = runTest {
        //GIVEN
        val idCity = "id:1234"
        val queryDays = 3
        val error = ApiError.InternalServerError(404)

        coEvery { remoteDataSource.getWeatherInfoById(idCity, queryDays) } throws error

        //WHEN
        val result = repository.getWeatherInfoById(idCity)

        //THEN
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())

        coVerify(exactly = 1) { remoteDataSource.getWeatherInfoById(idCity, queryDays) }
    }
}
