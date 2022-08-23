package com.cren90.android.logging

interface Logger {

    fun withData(vararg data: Pair<String, Any?>): Logger
    fun withData(data: Map<String, Any?>): Logger
    fun withException(e: Throwable): Logger
    fun fatal(message: String?, tag: String? = null)
    fun error(message: String?, tag: String? = null)
    fun warning(message: String?, tag: String? = null)
    fun info(message: String?, tag: String? = null)
    fun debug(message: String?, tag: String? = null)
    fun verbose(message: String?, tag: String? = null)
}