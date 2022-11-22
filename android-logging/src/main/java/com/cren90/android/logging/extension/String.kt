package com.cren90.android.logging.extension

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject

fun String.toPrettyJsonIfValid(): String {
    return try{
        JSONObject(this).toString(2)
    } catch (ignored: Throwable) {
        this
    }
}