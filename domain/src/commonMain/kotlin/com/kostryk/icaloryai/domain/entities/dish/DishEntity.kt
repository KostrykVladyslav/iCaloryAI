package com.kostryk.icaloryai.domain.entities.dish

import kotlinx.serialization.Serializable

@Serializable
data class DishEntity(
    val id: Long = 0L,
    val createdAt: String = "",
    val displayTime: String = "",
    val name: String = "",
    val imageByteArray: ByteArray? = null,
    val calories: Int = 0,
    val protein: Int = 0,
    val fats: Int = 0,
    val carbs: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DishEntity

        if (id != other.id) return false
        if (calories != other.calories) return false
        if (protein != other.protein) return false
        if (fats != other.fats) return false
        if (carbs != other.carbs) return false
        if (createdAt != other.createdAt) return false
        if (displayTime != other.displayTime) return false
        if (name != other.name) return false
        if (!imageByteArray.contentEquals(other.imageByteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + calories
        result = 31 * result + protein
        result = 31 * result + fats
        result = 31 * result + carbs
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + displayTime.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (imageByteArray?.contentHashCode() ?: 0)
        return result
    }
}