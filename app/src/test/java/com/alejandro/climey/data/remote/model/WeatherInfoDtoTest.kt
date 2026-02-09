package com.alejandro.climey.data.remote.model

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime


class WeatherInfoDtoTest {

    @Test
    fun `conditionDto toCondition apply correct transformation of the iconUrl`() {
        //GIVEN
        val conditionDto = ConditionDto(
            text = "Sunny",
            iconUrl = "//example.com/icon.png"
        )

        //WHEN
        val domain = conditionDto.toCondition()

        //THEN
        val expectedUrl = "https://example.com/icon.png"
        assertEquals(expectedUrl, domain.iconUrl)
    }

    @Test
    fun `forecastDayDto toForecastDay apply correct transformation of the date`() {
        //GIVEN
        val forecastDayDto = ForecastDayDto(
            date = "2023-09-01",
            day = DayInfoDto(
                maxTempC = 25.0,
                minTempC = 15.0,
                avgTempC = 20.0,
                condition = ConditionDto(
                    text = "Sunny",
                    iconUrl = "//example.com/icon.png"
                )
            ),
            hour = emptyList()
        )

        //WHEN
        val domain = forecastDayDto.toForecastDay()

        //THEN
        val expectedDate = LocalDate.of(2023, 9, 1)
        assertEquals(expectedDate, domain.date)
    }

    @Test
    fun `hourInfoDto toHourInfo apply correct transformation of the dateTime and iconUrl`() {
        //GIVEN
        val hourInfoDto = HourInfoDto(
            time = "2023-09-01 12:00",
            condition = ConditionDto(
                text = "Sunny",
                iconUrl = "//example.com/icon.png"
            ),
            tempC = 25.0
        )

        //WHEN
        val domain = hourInfoDto.toHourInfo()

        //THEN
        val expectedUrl = "https://example.com/icon.png"
        val expectedDateTime = LocalDateTime.of(2023, 9, 1, 12, 0)
        assertEquals(expectedUrl, domain.condition.iconUrl)
        assertEquals(expectedDateTime, domain.time)
    }
}