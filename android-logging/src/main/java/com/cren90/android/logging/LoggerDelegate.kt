package com.cren90.android.logging

import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate for retrieving a [Logger] implementation
 */
@Suppress("unused")
class LoggerDelegate<in T> : ReadOnlyProperty<T, Logger> {

    private lateinit var loggerEntryPoint: LoggerDelegateEntryPoint

    override operator fun getValue(thisRef: T, property: KProperty<*>): Logger {
        return getLogger(thisRef)
    }

    private fun getLogger(thisRef: T): Logger {
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
        return loggerEntryPoint.logger.let {
            if(thisRef is Any) {
                // Force unwrap nullable here because otherwise the compiler complains. We already
                // know it's not null because it is a subclass of Any and not Any?
                @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
                it.withTag(thisRef!!::class.java.simpleName)
            } else {
                it
            }
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
private interface LoggerDelegateEntryPoint {
    val logger: Logger
}