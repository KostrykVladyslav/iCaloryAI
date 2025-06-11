package com.kostryk.icaloryai.domain.converter.dish

import com.kostryk.icaloryai.domain.converter.Mapper
import com.kostryk.icaloryai.domain.database.entity.DishDatabaseEntity
import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.manager.time.DateTimeManager

class DishDatabaseToEntityMapper(private val dateTimeManager: DateTimeManager) :
    Mapper<DishDatabaseEntity, DishEntity>() {

    override fun map(from: DishDatabaseEntity): DishEntity {
        return DishEntity(
            id = from.id,
            name = from.name.orEmpty(),
            imageByteArray = from.image,
            calories = from.calories ?: 0,
            protein = from.protein ?: 0,
            carbs = from.carbs ?: 0,
            fats = from.fats ?: 0,
            createdAt = from.createdAt.orEmpty(),

            displayTime = dateTimeManager.formatDateTime(
                from.createdAt.orEmpty(),
                format = DateTimeManager.DEFAULT_DATE_TIME_FORMAT,
                targetFormat = DateTimeManager.DEFAULT_TIME_FORMAT
            )
        )
    }
}