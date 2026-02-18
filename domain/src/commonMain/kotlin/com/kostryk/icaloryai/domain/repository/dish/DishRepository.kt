package com.kostryk.icaloryai.domain.repository.dish

import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.entities.result.CreateDishStatusEntity
import com.kostryk.icaloryai.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

interface DishRepository : Repository {

    suspend fun countDishes(): Int

    suspend fun getAllDishesAsFlow(): Flow<List<DishEntity>>

    suspend fun getDishById(id: Long): Flow<DishEntity?>

    suspend fun updateDish(dish: DishEntity)

    suspend fun createDish(
        description: String,
        imageBytes: ByteArray?
    ): Flow<CreateDishStatusEntity>

}