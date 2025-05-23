package com.kostryk.icaloryai.utils

import android.content.Context

internal object ContextUtils {

    private var kmpAppContext: Context? = null

    val context
        get() = kmpAppContext
            ?: error("Android context has not been set. Please call setContext in your Application's onCreate.")

    fun setContext(context: Context?) {
        kmpAppContext = context
    }
}