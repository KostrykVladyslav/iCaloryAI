package com.kostryk.icaloryai.arch.di

import com.kostryk.icaloryai.data.di.dataModule
import com.kostryk.icaloryai.getPlatform
import org.koin.core.module.Module
import org.koin.dsl.module

val appMainModule = module {
    includes(appPlatformModule + dataModule)

    single { getPlatform() }

}

expect val appPlatformModule: Module