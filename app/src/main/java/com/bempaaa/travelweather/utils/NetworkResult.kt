package com.bempaaa.travelweather.utils

sealed class NetworkResult<DataType> {
    data class Success<DataType>(val data: DataType) : NetworkResult<DataType>()
    data class Error<DataType>(val e: Exception) : NetworkResult<DataType>()
}

object MissingBody : Exception()
data class HttpError(val code: Int) : Exception()