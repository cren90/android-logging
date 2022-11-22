package com.cren90.android.logging.extension

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

fun String.isValidJson(): Boolean {
    val gson = Gson()
    try{
        gson.fromJson(this, Any::class.java)
        return true
    } catch (ignored: JsonSyntaxException) {
        return false
    }
}