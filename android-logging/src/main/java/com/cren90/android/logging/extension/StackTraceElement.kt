@file:Suppress("unused")

package com.cren90.android.logging.extension

internal val StackTraceElement.simpleClassName: String
    get() = Class.forName(this.className).simpleName