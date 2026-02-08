package com.alejandro.climey.core.network

sealed class ApiError : Throwable() {
    class NetworkError : ApiError()
    class SerializationError : ApiError()
    class InternalServerError(errorCode: Int) : ApiError()

    class UnknownError : ApiError()
}