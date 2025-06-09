package com.kostryk.icaloryai.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    includes(dataPlatformModule)
}

expect val dataPlatformModule: Module