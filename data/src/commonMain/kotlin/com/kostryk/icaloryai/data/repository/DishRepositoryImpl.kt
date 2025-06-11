package com.kostryk.icaloryai.data.repository

import com.kostryk.icaloryai.data.ai.GeminiApi
import com.kostryk.icaloryai.domain.arch.coroutines.CoroutineLauncher
import com.kostryk.icaloryai.domain.arch.coroutines.impl.DefaultCoroutineLauncher
import com.kostryk.icaloryai.domain.converter.dish.DishDatabaseToEntityMapper
import com.kostryk.icaloryai.domain.database.dao.DishDatabaseDao
import com.kostryk.icaloryai.domain.database.entity.DishDatabaseEntity
import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.entities.result.CreateDishStatusEntity
import com.kostryk.icaloryai.domain.manager.time.DateTimeManager
import com.kostryk.icaloryai.domain.repository.dish.DishRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class DishRepositoryImpl(
    private val api: GeminiApi,
    private val dishDatabaseDao: DishDatabaseDao,
    private val dateTimeManager: DateTimeManager
) : DishRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext get() = Job() + Dispatchers.IO
    val launcher: CoroutineLauncher by lazy { DefaultCoroutineLauncher(this) }

    private val mapper by lazy { DishDatabaseToEntityMapper(dateTimeManager) }

    override suspend fun countDishes(): Int = dishDatabaseDao.count()

    override suspend fun getAllDishesAsFlow(): Flow<List<DishEntity>> =
        dishDatabaseDao.getAllAsFlow().map { list -> list.map { mapper.map(it) } }

    override suspend fun createDish(
        description: String,
        imageBytes: ByteArray?
    ): Flow<CreateDishStatusEntity> {
        val flow = MutableStateFlow<CreateDishStatusEntity>(CreateDishStatusEntity.Loading)
        launcher.launch {
            flow.emit(CreateDishStatusEntity.Loading)
            try {
                val generatedContent = api.generateContent(description, imageBytes)

                println("Gemini generated: $generatedContent \nDescription: ${generatedContent.text}")

                val response = generatedContent.text ?: throw Exception("No text content generated")
                val regex = Regex(DISH_PARAMS_REGEX)
                val match = regex.find(response)
                if (match != null) {
                    val (calories, protein, carbs, fats) = match.destructured
                    dishDatabaseDao.insertDish(
                        DishDatabaseEntity(
                            createdAt = dateTimeManager.getCurrentDateTime(),
                            name = getDishNameFromResponse(response),
                            image = imageBytes,
                            calories = calories.toInt(),
                            protein = protein.toInt(),
                            carbs = carbs.toInt(),
                            fats = fats.toInt()
                        )
                    )
                    flow.emit(CreateDishStatusEntity.Success)
                } else {
                    flow.emit(CreateDishStatusEntity.Error(response))
                    return@launch
                }
            } catch (e: Exception) {
                e.printStackTrace()
                flow.emit(CreateDishStatusEntity.Error(e.message.orEmpty()))
            }
        }
        return flow
    }

    private fun getDishNameFromResponse(response: String): String {
        return response.substringBefore(',').trim()
    }

    companion object {
        private const val DISH_PARAMS_REGEX =
            """calories:\s*(\d+), protein:\s*(\d+), carbs:\s*(\d+), fats:\s*(\d+)"""
    }
}