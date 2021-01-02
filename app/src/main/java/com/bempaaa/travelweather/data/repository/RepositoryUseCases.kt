package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.utils.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * List of APIs to access a generic repository representation
 * The repository can be accessed either using:
 * - a one-shot request
 * - periodic data polling
 *
 */
interface RepositoryUseCases<DataType> {

    suspend fun performRequest(
        key: String,
        call: suspend () -> Response<DataType>
    ): RequestResult<DataType>

    fun CoroutineScope.createFlowOf(
        key: String,
        pollingInterval: Long,
        call: suspend () -> Response<DataType>
    ): Flow<RequestResult<DataType>>
}