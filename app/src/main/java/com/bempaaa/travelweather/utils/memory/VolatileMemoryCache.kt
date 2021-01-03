package com.bempaaa.travelweather.utils.memory

import com.bempaaa.travelweather.utils.memory.MemoryCacheUseCases.Companion.THIRTY_SEC

/**
 * A simple implementation of MemoryCacheUseCases backed by a volatile hash map,
 * i.e. any cached value is lost when application is recreated
 */
class VolatileMemoryCache<T>(
    private val timeout: Long = THIRTY_SEC
) : MemoryCacheUseCases<T> {

    private val cachedData: HashMap<String, DataHolder<T>> = HashMap()

    override fun hasExpired(key: String): Boolean =
        cachedData[key]?.let { holder ->
            System.currentTimeMillis() - holder.timestamp > timeout
        } ?: true

    override fun addValue(key: String, value: T) {
        cachedData[key] = DataHolder(
            value = value,
            timestamp = System.currentTimeMillis()
        )
    }

    override fun getValue(key: String): T? = cachedData[key]?.value

    override fun clearCache() = cachedData.clear()

    private data class DataHolder<T>(
        val value: T,
        val timestamp: Long
    )
}
