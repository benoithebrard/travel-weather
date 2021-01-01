package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.utils.HttpError
import com.bempaaa.travelweather.utils.MissingBody
import com.bempaaa.travelweather.utils.NetworkResult
import retrofit2.Response

fun <DataType> Response<DataType>.toNetworkResult(): NetworkResult<DataType> =
    if (isSuccessful) {
        body()?.let { data ->
            NetworkResult.Success(data)
        } ?: NetworkResult.Error(MissingBody)
    } else NetworkResult.Error(HttpError(code()))