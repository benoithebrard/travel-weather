package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.utils.MemoryCache
import com.bempaaa.travelweather.utils.NetworkResult
import com.bempaaa.travelweather.utils.extensions.toNetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

open class CachedRepository<T>(
    timeout: Long = MemoryCache.THIRTY_SEC
) {
    private val cache: MemoryCache<T> = MemoryCache(timeout)

    suspend fun performRequest(
        key: String,
        call: suspend () -> Response<T>
    ): NetworkResult<T> {
        val cachedData = cache.getValue(key)
        if (cachedData != null && !cache.hasExpired(key)) {
            return NetworkResult.Success(cachedData)
        }

        return try {
            withContext(Dispatchers.IO) {
                val result = call().toNetworkResult()
                if (result is NetworkResult.Success<T>) {
                    cache.addValue(key, result.data)
                }
                result
            }
        } catch (e: IOException) {
            NetworkResult.Error(e)
        }
    }
}