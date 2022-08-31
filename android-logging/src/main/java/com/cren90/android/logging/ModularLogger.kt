package com.cren90.android.logging

import com.cren90.android.logging.extension.simpleClassName
import com.cren90.android.logging.strategies.LogStrategy

/**
 * [Logger] implementation that delegates it's logging to the provided logStrategies.
 */
@Suppress("unused")
class ModularLogger constructor(logStrategies: Collection<LogStrategy>? = null, tag: String? = null) : Logger {

    //region Constructors

    constructor(vararg logStrategies: LogStrategy, tag: String? = null) : this(logStrategies.toSet(), tag)

    private constructor(
        logger: ModularLogger,
        tag: String? = null,
        data: Map<String, Any?> = mapOf()
    ) : this(logger.strategies, tag) {
        this.data.putAll(logger.data)
        this.data.putAll(data)
    }

    private constructor(
        logger: ModularLogger,
        tag: String? = null,
        vararg data: Pair<String, Any?> = arrayOf()
    ) : this(logger, tag, data.toMap())

    // endregion Constructors

    private val strategies: MutableSet<LogStrategy> = logStrategies?.toMutableSet() ?: mutableSetOf()

    private var logTag: String? = tag

    private val data = mutableMapOf<String, Any?>()

    /**
     * Adds log strategies to this ModularLogger
     */
    fun addLogStrategies(vararg logStrategies: LogStrategy) {
        strategies.addAll(logStrategies)
    }

    /**
     * Removes log strategies from this ModularLogger
     */
    fun removeLogStrategies(vararg logStrategies: LogStrategy) {
        strategies.removeAll(logStrategies.toSet())
    }

    //region Logger Overrides

    override fun withTag(tag: String?): Logger {
        return ModularLogger(this, tag)
    }

    override fun withData(vararg data: Pair<String, Any?>): Logger {
        return ModularLogger(this, logTag, *data)
    }

    override fun withData(data: Map<String, Any?>): Logger {
        return ModularLogger(this, logTag, data)
    }

    override fun withException(e: Throwable): Logger {
        val data = "exception" to mapOf(
            "message" to e.message,
            "stacktrace" to e.stackTraceToString()
        )

        return ModularLogger(this, logTag, data)
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
        ?: logTag
        ?: Thread.currentThread().stackTrace[5].let { "${it.simpleClassName}.${it.methodName}" }
}