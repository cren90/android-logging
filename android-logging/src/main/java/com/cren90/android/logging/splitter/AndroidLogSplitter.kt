package com.cren90.android.logging.splitter

import com.cren90.android.logging.extension.isValidJson
import org.json.JSONObject

class AndroidLogSplitter: LogSplitter {
    override val maxLogLineBytesDefault: Int = 4000

    override fun split(message: String, maxLogLineBytes: Int, splitNewLines: Boolean): List<String> {
        val splitMessage = if(splitNewLines) {
            message.split("\n")
        } else {
            listOf(message)
        }.flatMap {
            if(it.isValidJson()) {
                JSONObject(it).toString(2).split("\n")
            } else {
                listOf(it)
            }
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