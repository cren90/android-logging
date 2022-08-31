package com.cren90.android.logging

import android.content.Context

interface LoggerContextProvider {
    val context: Context
}

class LoggerContextDelegate(override val context: Context): LoggerContextProvider {
    init {
        LoggerContextProviderHolder.loggerContextProvider = this
    }
}

object LoggerContextProviderHolder {
    var loggerContextProvider: LoggerContextProvider? = null
}