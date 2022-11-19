package com.cren90.android.logging.strategies

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.cren90.android.logging.splitter.AndroidLogSplitter
import com.cren90.android.logging.splitter.LogSplitter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Logging strategy for logging to Android Logcat
 */
class AndroidLoggingStrategy(override val logSplitter: LogSplitter = AndroidLogSplitter()) : LogStrategy {

    /**
     * Visible for unit testing only.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getDataString(data: Map<String, Any?>): String {
        val gson: Gson = GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create()
        val gsonType = object : TypeToken<HashMap<String, Any?>?>() {}.type

        @Suppress("UnnecessaryVariable")
        val gsonString: String = gson.toJson(data, gsonType)

        return gsonString
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getMessageWithData(message: String?, data: Map<String, Any?>?): String {
        return "${message ?: "No message"}${if (!data.isNullOrEmpty()) "\ndata:${getDataString(data)}" else ""}"
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun logWithFunction(tag: String, message: String?, data: Map<String, Any?>?, log: (logTag: String, logMessage: String) -> Unit) {
        logSplitter.split(
            message = getMessageWithData(message, data),
            splitNewLines = true
        ).forEach {
            log(tag, it)
        }
    }

    override fun fatal(message: String?, tag: String, data: Map<String, Any?>?) {
        logWithFunction(tag, message, data) { logTag, logMessage ->
            Log.wtf(logTag, logMessage)
        }
    }

    override fun error(message: String?, tag: String, data: Map<String, Any?>?) {
        logWithFunction(tag, message, data) { logTag, logMessage ->
            Log.e(logTag, logMessage)
        }
    }

    override fun warning(message: String?, tag: String, data: Map<String, Any?>?) {
        logWithFunction(tag, message, data) { logTag, logMessage ->
            Log.w(logTag, logMessage)
        }
    }

    override fun info(message: String?, tag: String, data: Map<String, Any?>?) {
        logWithFunction(tag, message, data) { logTag, logMessage ->
            Log.i(logTag, logMessage)
        }
    }

    override fun debug(message: String?, tag: String, data: Map<String, Any?>?) {
        logWithFunction(tag, message, data) { logTag, logMessage ->
            Log.d(logTag, logMessage)
        }
    }

    override fun verbose(message: String?, tag: String, data: Map<String, Any?>?) {
        logWithFunction(tag, message, data) { logTag, logMessage ->
            Log.v(logTag, logMessage)
        }
    }
}