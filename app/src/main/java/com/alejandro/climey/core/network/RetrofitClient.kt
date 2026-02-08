package com.alejandro.climey.core.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofitClient(baseUrl: String, apiKey: String, language: String): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(BaseConfigInterceptor(apiKey, language))
        .build()


    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

suspend fun <Dto, Domain> safeApiCall(
    call: suspend () -> Response<Dto>,
    transform: (Dto) -> Domain
): Result<Domain> = runCatching {
    val response = call()

    if (!response.isSuccessful) throw ApiError.InternalServerError(response.code())
    val body = response.body() ?: throw ApiError.SerializationError()

    transform(body)
}.recoverCatching { error ->
    throw when (error) {
        is ApiError -> error
        is java.net.UnknownHostException -> ApiError.NetworkError()
        is kotlinx.serialization.SerializationException,
        is com.google.gson.JsonSyntaxException -> ApiError.SerializationError()

        else -> ApiError.UnknownError()
    }
}
