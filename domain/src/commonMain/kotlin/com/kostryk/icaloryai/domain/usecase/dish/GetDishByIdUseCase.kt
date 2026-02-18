package com.kostryk.icaloryai.domain.usecase.dish

import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.repository.dish.DishRepository
import com.kostryk.icaloryai.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class GetDishByIdUseCase(private val repository: DishRepository) :
    UseCase<Flow<DishEntity?>> {

    suspend fun execute(id: Long): Flow<DishEntity?> {
        return repository.getDishById(id)
    }
}
