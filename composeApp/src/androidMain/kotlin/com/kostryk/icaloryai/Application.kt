package com.kostryk.icaloryai

import android.app.Application
import com.kostryk.icaloryai.arch.di.appMainModule
import com.kostryk.icaloryai.utils.ContextUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextUtils.setContext(this)

        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(appMainModule)
        }
    }
}