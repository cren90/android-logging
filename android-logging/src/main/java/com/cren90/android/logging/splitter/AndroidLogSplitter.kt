package com.cren90.android.logging.splitter

import com.cren90.android.logging.extension.toPrettyJsonIfValid

class AndroidLogSplitter: LogSplitter {
    override val maxLogLineBytesDefault: Int = 4000

    override fun split(message: String, maxLogLineBytes: Int, splitNewLines: Boolean): List<String> {
        val splitMessage = if(splitNewLines) {
            message.split("\n")
        } else {
            listOf(message)
        }.flatMap {
            it.toPrettyJsonIfValid().split("\n")
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