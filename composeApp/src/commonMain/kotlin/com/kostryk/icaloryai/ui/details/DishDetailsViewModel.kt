package com.kostryk.icaloryai.ui.details

import androidx.compose.ui.graphics.ImageBitmap
import com.kostryk.icaloryai.arch.utils.byteArrayToImageBitmapWithResize
import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.usecase.dish.GetDishByIdUseCase
import com.kostryk.icaloryai.domain.usecase.dish.UpdateDishUseCase
import com.kostryk.icaloryai.lifecycle.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DishDetailsState(
    val dish: DishEntity? = null,
    val image: ImageBitmap? = null,
    val editCalories: String = "",
    val editProtein: String = "",
    val editFat: String = "",
    val editCarbs: String = "",
    val isSaved: Boolean = false
)

class DishDetailsViewModel(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val updateDishUseCase: UpdateDishUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(DishDetailsState())
    val state: StateFlow<DishDetailsState> = _state.asStateFlow()

    fun loadDish(dishId: String) {
        val id = dishId.toLongOrNull() ?: return
        launch(context = Dispatchers.IO) {
            getDishByIdUseCase.execute(id).collect { dish ->
                if (dish != null) {
                    val image = dish.imageByteArray?.let { byteArrayToImageBitmapWithResize(it) }
                    _state.value = _state.value.copy(
                        dish = dish,
                        image = image,
                        editCalories = dish.calories.toString(),
                        editProtein = dish.protein.toString(),
                        editFat = dish.fats.toString(),
                        editCarbs = dish.carbs.toString()
                    )
                }
            }
        }
    }

    fun onCaloriesChanged(value: String) {
        _state.value = _state.value.copy(editCalories = value.filter { it.isDigit() })
    }

    fun onProteinChanged(value: String) {
        _state.value = _state.value.copy(editProtein = value.filter { it.isDigit() })
    }

    fun onFatChanged(value: String) {
        _state.value = _state.value.copy(editFat = value.filter { it.isDigit() })
    }

    fun onCarbsChanged(value: String) {
        _state.value = _state.value.copy(editCarbs = value.filter { it.isDigit() })
    }

    fun saveDish() {
        val dish = _state.value.dish ?: return
        val updated = dish.copy(
            calories = _state.value.editCalories.toIntOrNull() ?: 0,
            protein = _state.value.editProtein.toIntOrNull() ?: 0,
            fats = _state.value.editFat.toIntOrNull() ?: 0,
            carbs = _state.value.editCarbs.toIntOrNull() ?: 0
        )
        launch(context = Dispatchers.IO) {
            updateDishUseCase.execute(updated)
            _state.value = _state.value.copy(isSaved = true)
        }
    }
}
