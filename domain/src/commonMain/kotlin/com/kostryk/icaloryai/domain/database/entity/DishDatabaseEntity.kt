package com.kostryk.icaloryai.domain.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
class DishDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val createdAt: String? = null,
    val name: String? = null,
    val imageBase64: String? = null,
    val calories: Int? = null,
    val protein: Int? = null,
    val fat: Int? = null,
    val carbohydrates: Int? = null
)