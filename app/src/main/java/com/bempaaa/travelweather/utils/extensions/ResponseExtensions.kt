package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.utils.HttpError
import com.bempaaa.travelweather.utils.MissingBody
import com.bempaaa.travelweather.utils.RequestResult
import retrofit2.Response

fun <DataType> Response<DataType>.toNetworkResult(): RequestResult<DataType> =
    if (isSuccessful) {
        body()?.let { data ->
            RequestResult.Success(data)
        } ?: RequestResult.Error(MissingBody)
    } else RequestResult.Error(HttpError(code()))