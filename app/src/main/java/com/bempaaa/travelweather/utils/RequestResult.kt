package com.bempaaa.travelweather.utils

sealed class RequestResult<DataType> {
    data class Success<DataType>(val data: DataType) : RequestResult<DataType>()
    data class Error<DataType>(val e: Exception) : RequestResult<DataType>()
}

object MissingBody : Exception()
data class HttpError(val code: Int) : Exception()