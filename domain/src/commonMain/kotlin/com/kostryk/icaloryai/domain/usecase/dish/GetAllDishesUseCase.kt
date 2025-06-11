package com.kostryk.icaloryai.domain.usecase.dish

import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.repository.dish.DishRepository
import com.kostryk.icaloryai.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class GetAllDishesUseCase(private val repository: DishRepository) :
    UseCase<Flow<List<DishEntity>>> {

    suspend fun execute(): Flow<List<DishEntity>> {
        return repository.getAllDishesAsFlow()
    }
}