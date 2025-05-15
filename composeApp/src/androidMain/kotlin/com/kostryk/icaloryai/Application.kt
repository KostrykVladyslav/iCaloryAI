package com.kostryk.icaloryai

import android.app.Application
import com.kostryk.icaloryai.utils.ContextUtils

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextUtils.setContext(this)
    }
}