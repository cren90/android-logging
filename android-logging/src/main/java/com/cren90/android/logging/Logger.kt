package com.cren90.android.logging

/**
 * Wrapper interface around logging various messages
 */
@Suppress("unused")
interface Logger {

    /**
     * Returns a new Logger instance
     */
    fun withTag(tag: String?): Logger

    /**
     * Returns a new Logger instance with the associated data. Depending on the strategy this data
     * may be logged with the message ormight be submitted as ancillary data to help with
     * aggregations.
     */
    fun withData(vararg data: Pair<String, Any?>): Logger

    /**
     * Returns a new Logger instance with the associated data. Depending on the strategy this data
     * may be logged with the message ormight be submitted as ancillary data to help with
     * aggregations.
     */
    fun withData(data: Map<String, Any?>): Logger

    /**
     * Returns a new Logger instance with the associated exception data. Depending on the strategy
     * this data may be logged with the message or might be submitted as ancillary data to help with
     * aggregations.
     */
    fun withException(e: Throwable): Logger

    /**
     * Logs a fatal error and optional tag
     */
    fun fatal(message: String?, tag: String? = null)

    /**
     * Logs an error and optional tag
     */
    fun error(message: String?, tag: String? = null)

    /**
     * Logs a warning message and optional tag
     */
    fun warning(message: String?, tag: String? = null)

    /**
     * Logs an info message and optional tag
     */
    fun info(message: String?, tag: String? = null)

    /**
     * Logs a debug message and optional tag
     */
    fun debug(message: String?, tag: String? = null)

    /**
     * Logs a verbose message and optional tag
     */
    fun verbose(message: String?, tag: String? = null)
}