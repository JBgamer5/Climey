package com.alejandro.climey.di

import com.alejandro.climey.BuildConfig
import com.alejandro.climey.core.network.getRetrofitClient
import com.alejandro.climey.core.utils.getCurrentLangCode
import com.alejandro.climey.data.remote.WeatherRemoteDataSource
import com.alejandro.climey.data.repository.WeatherRepositoryImpl
import com.alejandro.climey.domain.repository.WeatherRepository
import com.alejandro.climey.domain.usecases.GetWeatherInfoById
import com.alejandro.climey.domain.usecases.SearchLocation
import com.alejandro.climey.presentation.search.SearchViewModel
import com.alejandro.climey.presentation.weatherInfo.WeatherInfoViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.create

val dataModule = module {
    single {
        val client = getRetrofitClient(
            BuildConfig.base_api_url,
            BuildConfig.api_key,
            getCurrentLangCode()
        )

        client.create<WeatherRemoteDataSource>()
    }

    singleOf(::WeatherRepositoryImpl) { bind<WeatherRepository>() }
}

val domainModule = module {
    singleOf(::SearchLocation)
    singleOf(::GetWeatherInfoById)
}

val presentationModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::WeatherInfoViewModel)
}