package com.kostryk.icaloryai.arch.di

import com.kostryk.icaloryai.data.di.dataModule
import com.kostryk.icaloryai.getPlatform
import com.kostryk.icaloryai.ui.main.MainViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appMainModule = module {
    includes(appPlatformModule + dataModule)

    single { getPlatform() }

    viewModel { MainViewModel() }
}

expect val appPlatformModule: Module