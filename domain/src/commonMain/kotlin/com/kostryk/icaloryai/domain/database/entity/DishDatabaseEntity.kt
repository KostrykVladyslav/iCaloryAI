package com.kostryk.icaloryai.domain.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
class DishDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val createdAt: String? = null,
    val name: String? = null,
    val image: ByteArray? = null,
    val calories: Int? = null,
    val protein: Int? = null,
    val fats: Int? = null,
    val carbs: Int? = null
)