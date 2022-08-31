package com.cren90.android.logging

import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
class LoggerDelegate<in T : Any> : ReadOnlyProperty<T, Logger> {

    private lateinit var loggerEntryPoint: LoggerDelegateEntryPoint

    override fun getValue(thisRef: T, property: KProperty<*>): Logger {
        if (!::loggerEntryPoint.isInitialized)
            synchronized(loggerEntryPoint) {
                if (!::loggerEntryPoint.isInitialized) {
                    LoggerContextProviderHolder.loggerContextProvider?.let {
                        loggerEntryPoint = EntryPoints.get(
                            it.context.applicationContext,
                            LoggerDelegateEntryPoint::class.java
                        )

                    } ?: run {
                        throw IllegalStateException(
                            "Logger context is not being provided. " +
                                    "Ensure the Application is delegating to LoggerContextDelegate."
                        )
                    }
                }
            }
        return loggerEntryPoint.logger
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
private interface LoggerDelegateEntryPoint {
    val logger: Logger
}