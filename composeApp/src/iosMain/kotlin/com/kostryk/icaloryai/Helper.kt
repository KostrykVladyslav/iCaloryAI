package com.kostryk.icaloryai

import com.kostryk.icaloryai.arch.di.appMainModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appMainModule)
    }
}