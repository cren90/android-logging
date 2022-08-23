package com.cren90.android.logging.strategies

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class AndroidLoggingStrategy: LogStrategy {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getDataString(data: Map<String, Any?>): String {
        val gson: Gson = GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create()
        val gsonType = object : TypeToken<HashMap<String, Any?>?>() {}.type

        @Suppress("UnnecessaryVariable")
        val gsonString: String = gson.toJson(data, gsonType)

        return gsonString
    }

    private fun getMessageWithData(message: String?, data: Map<String, Any?>?): String {
        return "${ message ?: "No message" }${ if(!data.isNullOrEmpty())"\ndata:${ getDataString(data) }" else ""}"
    }

    /**
     * Logs a fatal error with the given [tag]
     */
    override fun fatal(message: String?, tag: String, data: Map<String, Any?>?) {
        Log.wtf(tag, getMessageWithData(message, data))
    }

    override fun error(message: String?, tag: String, data: Map<String, Any?>?) {
        Log.e(tag, getMessageWithData(message, data))
    }

    override fun warning(message: String?, tag: String, data: Map<String, Any?>?) {
        Log.w(tag, getMessageWithData(message, data))
    }

    override fun info(message: String?, tag: String, data: Map<String, Any?>?) {
        Log.i(tag, getMessageWithData(message, data))
    }

    override fun debug(message: String?, tag: String, data: Map<String, Any?>?) {
        Log.d(tag, getMessageWithData(message, data))
    }

    override fun verbose(message: String?, tag: String, data: Map<String, Any?>?) {
        Log.v(tag, getMessageWithData(message, data))
    }


}