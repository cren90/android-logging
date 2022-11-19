package com.cren90.android.logging.strategies

import com.cren90.android.logging.splitter.AndroidLogSplitter
import org.junit.Test
import java.util.*


class AndroidLoggingStrategyTests {

    val uuid = UUID.randomUUID()
    val data = mapOf<String, Any?>(
        "key1" to mapOf<String, Any?>(
            "innerkey1" to 22,
            "innerkey2" to 9.5,
            "innerkey3" to null
        ),
        "key2" to "value2",
        "key3" to uuid
    )

    @Test
    fun testDataMapSerialization() {

        val expected = "{\n" +
                       "  \"key1\": {\n" +
                       "    \"innerkey1\": 22,\n" +
                       "    \"innerkey2\": 9.5,\n" +
                       "    \"innerkey3\": null\n" +
                       "  },\n" +
                       "  \"key2\": \"value2\",\n" +
                       "  \"key3\": \"$uuid\"\n" +
                       "}"

        val string = AndroidLoggingStrategy().getDataString(data)

        assert(string == expected)
    }

    @Test
    fun testReallyLongStringSplit() {
        val message = "A".repeat(6000).plus("\nBBB")
        val split = AndroidLogSplitter().split(message = message, splitNewLines = true)
        assert(split.size == 3)
        assert(split[0].length == 4000)
        assert(split[1].length == 2000)
        assert(split[2].length == 3)
    }
}