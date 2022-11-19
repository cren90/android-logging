package com.cren90.android.logging.splitter

interface LogSplitter {
    val maxLogLineBytesDefault: Int
    fun split(message: String, maxLogLineBytes: Int = maxLogLineBytesDefault, splitNewLines: Boolean = true): List<String>
}