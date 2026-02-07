package com.alejandro.climey.core.network

import okhttp3.Interceptor
import okhttp3.Response

class BaseConfigInterceptor(
    private val apiKey: String,
    private val language: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithParams = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .addQueryParameter("lang", language)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(urlWithParams)
            .build()

        return chain.proceed(newRequest)
    }
}