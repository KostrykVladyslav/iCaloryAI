package com.kostryk.icaloryai.arch.di

import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.arch.manager.theme.ThemeManagerImpl
import com.kostryk.icaloryai.arch.settings.SettingsManagerImpl
import com.kostryk.icaloryai.data.ai.GeminiApi
import com.kostryk.icaloryai.data.manager.DateTimeManagerImpl
import com.kostryk.icaloryai.data.repository.DishRepositoryImpl
import com.kostryk.icaloryai.domain.database.dao.DishDatabaseDao
import com.kostryk.icaloryai.domain.manager.settings.SettingsManager
import com.kostryk.icaloryai.domain.manager.time.DateTimeManager
import com.kostryk.icaloryai.domain.repository.dish.DishRepository
import com.kostryk.icaloryai.domain.usecase.dish.CreateDishUseCase
import com.kostryk.icaloryai.domain.usecase.dish.GetAllDishesUseCase
import org.koin.dsl.module

private val api = module {
    single<GeminiApi> { GeminiApi() }
}

private val repository = module {
    single<DishRepository> {
        DishRepositoryImpl(
            api = get<GeminiApi>(),
            dishDatabaseDao = get<DishDatabaseDao>(),
            dateTimeManager = get<DateTimeManager>()
        )
    }
}

private val manager = module {
    single<DateTimeManager> { DateTimeManagerImpl() }
    single<SettingsManager> { SettingsManagerImpl() }
    single<ThemeManager> { ThemeManagerImpl(settingsManager = get<SettingsManager>()) }
}

private val useCase = module {
    factory<CreateDishUseCase> { CreateDishUseCase(repository = get<DishRepository>()) }
    factory<GetAllDishesUseCase> { GetAllDishesUseCase(repository = get<DishRepository>()) }
}

val dataModule = module {
    includes(api + repository + manager + useCase)
}