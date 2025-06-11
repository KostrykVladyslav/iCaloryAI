package com.kostryk.icaloryai.domain.entities.result

sealed class CreateDishStatusEntity {

    object Success : CreateDishStatusEntity()

    object Loading : CreateDishStatusEntity()

    data class Error(
        val message: String
    ) : CreateDishStatusEntity()
}