package com.kostryk.icaloryai.arch.di

import com.kostryk.icaloryai.arch.database.getDatabaseBuilder
import com.kostryk.icaloryai.domain.database.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appPlatformModule: Module = module {
    single<AppDatabase> { getDatabaseBuilder().build() }
}