package com.kostryk.icaloryai.arch.di

import com.kostryk.icaloryai.Platform
import com.kostryk.icaloryai.arch.database.AppDatabase
import com.kostryk.icaloryai.domain.database.dao.DishDatabaseDao
import com.kostryk.icaloryai.domain.usecase.dish.CreateDishUseCase
import com.kostryk.icaloryai.domain.usecase.dish.GetAllDishesUseCase
import com.kostryk.icaloryai.getPlatform
import com.kostryk.icaloryai.ui.main.MainViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appMainModule = module {
    includes(appPlatformModule + dataModule)

    single<Platform> { getPlatform() }
    single<DishDatabaseDao> { get<AppDatabase>().getDishDao() }

    viewModel<MainViewModel> {
        MainViewModel(
            getAllDishesUseCase = get<GetAllDishesUseCase>(),
            createDishUseCase = get<CreateDishUseCase>()
        )
    }
}

expect val appPlatformModule: Module