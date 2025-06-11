package com.kostryk.icaloryai.domain.usecase.dish

import com.kostryk.icaloryai.domain.entities.result.CreateDishStatusEntity
import com.kostryk.icaloryai.domain.repository.dish.DishRepository
import com.kostryk.icaloryai.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class CreateDishUseCase(private val repository: DishRepository) :
    UseCase<Flow<CreateDishStatusEntity>> {

    suspend fun execute(description: String, imageBytes: ByteArray?): Flow<CreateDishStatusEntity> {
        return repository.createDish(description, imageBytes)
    }
}