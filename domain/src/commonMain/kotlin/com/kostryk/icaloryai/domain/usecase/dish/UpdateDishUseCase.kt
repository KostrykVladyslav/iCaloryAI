package com.kostryk.icaloryai.domain.usecase.dish

import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.repository.dish.DishRepository
import com.kostryk.icaloryai.domain.usecase.UseCase

class UpdateDishUseCase(private val repository: DishRepository) :
    UseCase<Unit> {

    suspend fun execute(dish: DishEntity) {
        repository.updateDish(dish)
    }
}
