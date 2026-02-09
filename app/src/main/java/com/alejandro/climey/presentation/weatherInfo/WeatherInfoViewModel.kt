package com.alejandro.climey.presentation.weatherInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.climey.domain.model.WeatherInfo
import com.alejandro.climey.domain.usecases.GetWeatherInfoById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherInfoViewModel(
    val getWeatherInfoById: GetWeatherInfoById
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherInfoState())
    val state = _state.asStateFlow()

    fun loadInformation(id: Int) {
        viewModelScope.launch {
            _state.value = WeatherInfoState(isLoading = true)
            getWeatherInfoById(id).fold(
                onSuccess = {
                    _state.value = WeatherInfoState(data = it)
                },
                onFailure = {
                    _state.value = WeatherInfoState(error = it)
                }
            )
        }
    }
}

data class WeatherInfoState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val data: WeatherInfo? = null
)