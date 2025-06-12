package com.kostryk.icaloryai.ui.main

import androidx.compose.ui.graphics.ImageBitmap
import com.kostryk.icaloryai.arch.manager.file.SharedImage
import com.kostryk.icaloryai.arch.utils.byteArrayToImageBitmap
import com.kostryk.icaloryai.arch.utils.byteArrayToImageBitmapWithResize
import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.entities.result.CreateDishStatusEntity
import com.kostryk.icaloryai.domain.usecase.dish.CreateDishUseCase
import com.kostryk.icaloryai.domain.usecase.dish.GetAllDishesUseCase
import com.kostryk.icaloryai.lifecycle.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val getAllDishesUseCase: GetAllDishesUseCase,
    private val createDishUseCase: CreateDishUseCase
) : BaseViewModel() {

    private val _dishesWithImages =
        MutableStateFlow<List<Pair<ImageBitmap?, DishEntity>>>(emptyList())
    val dishesWithImages: Flow<List<Pair<ImageBitmap?, DishEntity>>> = _dishesWithImages

    private val _createDishResult = MutableStateFlow<CreateDishStatusEntity?>(null)
    val createDishResult: Flow<CreateDishStatusEntity?> = _createDishResult

    init {
        launch(context = Dispatchers.IO) { getAllDishes() }
    }

    fun handleCameraResult(image: SharedImage?) {
        launch(context = Dispatchers.IO) { createDish(imageBytes = image?.toByteArray()) }
    }

    fun handleGalleryResult(image: SharedImage?) {
        launch(context = Dispatchers.IO) { createDish(imageBytes = image?.toByteArray()) }
    }

    private suspend fun getAllDishes() {
        getAllDishesUseCase.execute().collect { dishesList ->
            _dishesWithImages.emit(dishesList.map { dish ->
                val parsedImageBitmap =
                    dish.imageByteArray?.let { byteArrayToImageBitmapWithResize(it) }
                Pair(parsedImageBitmap, dish)
            })
        }
    }

    private suspend fun createDish(description: String = "", imageBytes: ByteArray?) {
        createDishUseCase.execute(description, imageBytes)
            .collect { status -> _createDishResult.emit(status) }
    }
}