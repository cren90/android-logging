package com.cren90.android.logging

import com.cren90.android.logging.extension.simpleClassName
import com.cren90.android.logging.strategies.LogStrategy

@Suppress("unused")
class ModularLogger constructor(logStrategies: Collection<LogStrategy>) : Logger {

    //region Constructors

    constructor(vararg logStrategies: LogStrategy) : this(logStrategies.toSet())

    private constructor(
        logger: ModularLogger,
        data: Map<String, Any?>
    ) : this(logger.strategies) {
        this.data.putAll(logger.data)
        this.data.putAll(data)
    }

    private constructor(
        logger: ModularLogger,
        vararg data: Pair<String, Any?>
    ) : this(logger, data.toMap())

    // endregion Constructors

    private val strategies: MutableSet<LogStrategy> = logStrategies.toMutableSet()

    private val data = mutableMapOf<String, Any?>()

    fun addLogStrategies(vararg logStrategies: LogStrategy) {
        strategies.addAll(logStrategies)
    }

    fun removeLogStrategies(vararg logStrategies: LogStrategy) {
        strategies.removeAll(logStrategies.toSet())
    }

    //region Logger Overrides

    override fun withData(vararg data: Pair<String, Any?>): Logger {
        return ModularLogger(this, *data)
    }

    override fun withData(data: Map<String, Any?>): Logger {
        return ModularLogger(this, data)
    }

    override fun withException(e: Throwable): Logger {
        val data = "exception" to mapOf(
            "message" to e.message,
            "stacktrace" to e.stackTraceToString()
        )

        return ModularLogger(this, data)
    }

    override fun fatal(message: String?, tag: String?) {
        strategies.forEach {
            it.fatal(message, getTag(tag), data)
        }
    }

    override fun error(message: String?, tag: String?) {
        strategies.forEach {
            it.error(message, getTag(tag), data)
        }
    }

    override fun warning(message: String?, tag: String?) {
        strategies.forEach {
            it.warning(message, getTag(tag), data)
        }
    }

    override fun info(message: String?, tag: String?) {
        strategies.forEach {
            it.info(message, getTag(tag), data)
        }
    }

    override fun debug(message: String?, tag: String?) {
        strategies.forEach {
            it.debug(message, getTag(tag), data)
        }
    }

    override fun verbose(message: String?, tag: String?) {
        strategies.forEach {
            it.verbose(message, getTag(tag), data)
        }
    }

    //endregion Logger Overrides

    private fun getTag(explicitTag: String?) = explicitTag
        ?: Thread.currentThread().stackTrace[5].let { "${it.simpleClassName}.${it.methodName}" }
}