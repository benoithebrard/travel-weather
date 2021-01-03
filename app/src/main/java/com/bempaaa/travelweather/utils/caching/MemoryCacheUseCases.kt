package com.bempaaa.travelweather.utils.caching

/**
 * List of APIs to access a generic cache representation
 */
interface MemoryCacheUseCases<DataType> {

    fun hasExpired(key: String): Boolean

    fun addValue(key: String, value: DataType)

    fun getValue(key: String): DataType?

    fun clearCache()

    companion object {
        const val TEN_SEC: Long = 10 * 1000
        const val THIRTY_SEC: Long = 30 * 1000
        const val ONE_MIN: Long = 60 * 1000
        const val FIVE_MIN: Long = 5 * 60 * 1000
        const val THIRTY_MIN: Long = 30 * 60 * 1000
        const val THREE_HOURS: Long = 3 * 60 * 60 * 1000
    }
}