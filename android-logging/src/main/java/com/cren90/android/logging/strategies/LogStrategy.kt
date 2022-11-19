package com.cren90.android.logging.strategies

import com.cren90.android.logging.splitter.LogSplitter

/**
 * Wrapper interface around logging various messages to different locations (Logcat, Splunk, Crashlytics, Datadog, etc)
 */
interface LogStrategy {
    val logSplitter: LogSplitter

    /**
     * Logs a fatal [message] with a given [tag] and any associated [data]
     */
    fun fatal(message: String?, tag: String, data: Map<String, Any?>? = null)

    /**
     * Logs a error [message] with a given [tag] and any associated [data]
     */
    fun error(message: String?, tag: String, data: Map<String, Any?>? = null)

    /**
     * Logs a warning [message] with a given [tag] and any associated [data]
     */
    fun warning(message: String?, tag: String, data: Map<String, Any?>? = null)

    /**
     * Logs a info( [message] with a given [tag] and any associated [data]
     */
    fun info(message: String?, tag: String, data: Map<String, Any?>? = null)

    /**
     * Logs a debug [message] with a given [tag] and any associated [data] will only occur in debug builds
     */
    fun debug(message: String?, tag: String, data: Map<String, Any?>? = null)

    /**
     * Logs a verbose [message] with a given [tag] and any associated [data] will only occur in debug builds
     */
    fun verbose(message: String?, tag: String, data: Map<String, Any?>? = null)
}