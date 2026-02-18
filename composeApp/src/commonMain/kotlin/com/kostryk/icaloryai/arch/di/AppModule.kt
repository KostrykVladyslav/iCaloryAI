package com.kostryk.icaloryai.arch.di

import com.kostryk.icaloryai.Platform
import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.domain.database.dao.DishDatabaseDao
import com.kostryk.icaloryai.domain.database.database.AppDatabase
import com.kostryk.icaloryai.domain.manager.settings.SettingsManager
import com.kostryk.icaloryai.domain.manager.time.DateTimeManager
import com.kostryk.icaloryai.domain.usecase.dish.CreateDishUseCase
import com.kostryk.icaloryai.domain.usecase.dish.GetAllDishesUseCase
import com.kostryk.icaloryai.domain.usecase.dish.GetDishByIdUseCase
import com.kostryk.icaloryai.domain.usecase.dish.UpdateDishUseCase
import com.kostryk.icaloryai.getPlatform
import com.kostryk.icaloryai.ui.details.DishDetailsViewModel
import com.kostryk.icaloryai.ui.main.MainViewModel
import com.kostryk.icaloryai.ui.profile.ProfileViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appMainModule = module {
    includes(appPlatformModule + dataModule)

    single<Platform> { getPlatform() }
    single<DishDatabaseDao> { get<AppDatabase>().getDishDao() }

    viewModel<MainViewModel> {
        MainViewModel(
            getAllDishesUseCase = get<GetAllDishesUseCase>(),
            createDishUseCase = get<CreateDishUseCase>(),
            dateManager = get<DateTimeManager>(),
            settingsManager = get<SettingsManager>()
        )
    }
    viewModel<DishDetailsViewModel> {
        DishDetailsViewModel(
            getDishByIdUseCase = get<GetDishByIdUseCase>(),
            updateDishUseCase = get<UpdateDishUseCase>()
        )
    }
    viewModel<ProfileViewModel> {
        ProfileViewModel(
            settingsManager = get<SettingsManager>(),
            themeManager = get<ThemeManager>()
        )
    }
}

expect val appPlatformModule: Module