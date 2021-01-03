package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.caching.MemoryCacheUseCases.Companion.ONE_MIN
import com.bempaaa.travelweather.utils.caching.VolatileMemoryCache
import com.bempaaa.travelweather.utils.extensions.toNetworkResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

/**
 * A base data repository class to access and cache data:
 * 1) check for cached data and returns it if not expired
 * 2) otherwise, perform the network call on an IO coroutine
 * 3) store returned value in cache
 *
 * The returned result is either:
 * - a success type containing the payload data
 * - an error type containing an exception
 *
 */
open class CachedRepository<T>(
    timeout: Long = 0
) : RepositoryUseCases<T> {
    private val cache: VolatileMemoryCache<T> = VolatileMemoryCache(timeout)

    override suspend fun performRequest(
        key: String,
        call: suspend () -> Response<T>
    ): RequestResult<T> {
        val cachedData = cache.getValue(key)
        if (cachedData != null && !cache.hasExpired(key)) {
            return RequestResult.Success(cachedData)
        }

        return try {
            withContext(Dispatchers.IO) {
                val result = call().toNetworkResult()
                if (result is RequestResult.Success<T>) {
                    cache.addValue(key, result.data)
                }
                result
            }
        } catch (e: IOException) {
            RequestResult.Error(e)
        }
    }

    override fun CoroutineScope.createFlowOf(
        key: String,
        pollingInterval: Long,
        call: suspend () -> Response<T>
    ): Flow<RequestResult<T>> = flow {
        var isClosed = false

        while (!isClosed) {
            try {
                emit(performRequest(key, call))
                delay(pollingInterval)
            } catch (e: CancellationException) {
                isClosed = true
                cache.clearCache()
            }
        }
    }
}