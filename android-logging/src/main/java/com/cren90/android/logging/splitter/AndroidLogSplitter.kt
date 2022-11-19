package com.cren90.android.logging.splitter

class AndroidLogSplitter: LogSplitter {
    override val maxLogLineBytesDefault: Int = 4000

    override fun split(message: String, maxLogLineBytes: Int, splitNewLines: Boolean): List<String> {
        val splitMessage = if(splitNewLines) {
            message.split("\n")
        } else {
            listOf(message)
        }

        return splitMessage.flatMap {
            if(it.length > maxLogLineBytes) {
                it.chunked(maxLogLineBytes)
            } else {
                listOf(it)
            }
        }
    }
}