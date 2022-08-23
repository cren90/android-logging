package com.cren90.android.logging.strategies

import org.junit.Test
import java.util.*


class AndroidLoggingStrategyTests {

    @Test
    fun testDataMapSerialization() {
        val uuid = UUID.randomUUID()
        val map = mapOf<String, Any?>(
            "key1" to mapOf<String, Any?>(
                "innerkey1" to 22,
                "innerkey2" to 9.5,
                "innerkey3" to null
            ),
            "key2" to "value2",
            "key3" to uuid
        )

        val expected = "{\n" +
                       "  \"key1\": {\n" +
                       "    \"innerkey1\": 22,\n" +
                       "    \"innerkey2\": 9.5,\n" +
                       "    \"innerkey3\": null\n" +
                       "  },\n" +
                       "  \"key2\": \"value2\",\n" +
                       "  \"key3\": \"$uuid\"\n" +
                       "}"

        val string = AndroidLoggingStrategy().getDataString(map)

        assert(string == expected)
    }

}