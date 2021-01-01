package com.bempaaa.travelweather.utils

class MemoryCache<T>(
    private val timeout: Long = THIRTY_SEC
) {

    private val cachedData: HashMap<String, DataHolder<T>> = HashMap()

    fun hasExpired(key: String): Boolean =
        cachedData[key]?.let { holder ->
            System.currentTimeMillis() - holder.timestamp > timeout
        } ?: true

    fun addValue(key: String, value: T) {
        cachedData[key] = DataHolder(
            value = value,
            timestamp = System.currentTimeMillis()
        )
    }

    fun getValue(key: String): T? = cachedData[key]?.value

    private data class DataHolder<T>(
        val value: T,
        val timestamp: Long
    )

    companion object {
        const val THIRTY_SEC: Long = 30 * 1000
        const val THIRTY_MIN: Long = 30 * 60 * 1000
        const val THREE_HOURS: Long = 3 * 60 * 60 * 1000
    }
}
