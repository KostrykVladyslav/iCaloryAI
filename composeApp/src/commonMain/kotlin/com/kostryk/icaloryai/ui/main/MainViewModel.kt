package com.kostryk.icaloryai.ui.main

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import com.kostryk.icaloryai.arch.manager.file.SharedImage
import com.kostryk.icaloryai.arch.utils.byteArrayToImageBitmapWithResize
import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.domain.entities.result.CreateDishStatusEntity
import com.kostryk.icaloryai.domain.manager.settings.SettingsManager
import com.kostryk.icaloryai.domain.manager.time.DateTimeManager
import com.kostryk.icaloryai.domain.usecase.dish.CreateDishUseCase
import com.kostryk.icaloryai.domain.usecase.dish.GetAllDishesUseCase
import com.kostryk.icaloryai.lifecycle.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.coroutineContext

class MainViewModel(
    private val getAllDishesUseCase: GetAllDishesUseCase,
    private val createDishUseCase: CreateDishUseCase,
    private val dateManager: DateTimeManager,
    private val settingsManager: SettingsManager
) : BaseViewModel() {

    private val _dishesWithImages =
        MutableStateFlow<List<Pair<ImageBitmap?, DishEntity>>>(emptyList())
    val dishesWithImages: Flow<List<Pair<ImageBitmap?, DishEntity>>> = _dishesWithImages

    private val _createDishResult = MutableStateFlow<CreateDishStatusEntity?>(null)
    val createDishResult: Flow<CreateDishStatusEntity?> = _createDishResult

    private val _selectedDate = MutableStateFlow<String>(getCurrentDate())
    val selectedDate: Flow<String> = _selectedDate

    private val _selectedWeekIndex = MutableStateFlow<Int>(0)
    val selectedWeekIndex: Flow<Int> = _selectedWeekIndex

    private val _weeks = MutableStateFlow<List<List<String>>>(dateManager.createLastWeeks())
    val weeks: Flow<List<List<String>>> = _weeks

    private val _caloriesSpend = MutableStateFlow<Int>(0)
    val caloriesSpend: Flow<Int> = _caloriesSpend
    private val _proteinSpend = MutableStateFlow<Int>(0)
    val proteinSpend: Flow<Int> = _proteinSpend
    private val _farSpend = MutableStateFlow<Int>(0)
    val farSpend: Flow<Int> = _farSpend
    private val _carbsSpend = MutableStateFlow<Int>(0)
    val carbsSpend: Flow<Int> = _carbsSpend

    init {
        onDateSelected(dateManager.getCurrentDateTime(_selectedDate.value))
    }

    fun onDateSelected(date: String) {
        _selectedDate.value = date
        loadDishesByDate(date)
    }

    fun onWeekChanged(index: Int) {
        _selectedWeekIndex.value = index
        val selectedWeek = _weeks.value.getOrNull(index) ?: return
        if (selectedWeek.isNotEmpty()) {
            if (index != 0) {
                onDateSelected(selectedWeek.first())
            } else {
                onDateSelected(getCurrentDate())
            }
        }
    }

    fun getCurrentDate(format: String = DateTimeManager.DEFAULT_DATE_FORMAT): String {
        return dateManager.getCurrentDateTime(format)
    }

    fun handleCameraResult(image: SharedImage?) {
        launch(context = Dispatchers.IO) { createDish(imageBytes = image?.toByteArray()) }
    }

    fun handleGalleryResult(image: SharedImage?) {
        launch(context = Dispatchers.IO) { createDish(imageBytes = image?.toByteArray()) }
    }

    private fun loadDishesByDate(date: String) {
        launch(context = Dispatchers.IO) {
            getAllDishesUseCase.execute().collect { dishesList ->
                handleDishes(dishesList, date)
            }
        }
    }

    private suspend fun handleDishes(dishesList: List<DishEntity>, date: String) {
        val filteredByDateDishes = dishesList.filter { it.createdAt.contains(date) }
        filteredByDateDishes.map {
            async {
                val parsedImageBitmap =
                    it.imageByteArray?.let { byteArrayToImageBitmapWithResize(it) }
                Pair(parsedImageBitmap, it)
            }
        }.awaitAll()
            .apply {
                _dishesWithImages.emit(this)
                _caloriesSpend.value = sumOf { it.second.calories }
                _proteinSpend.value = sumOf { it.second.protein }
                _farSpend.value = sumOf { it.second.fats }
                _carbsSpend.value = sumOf { it.second.carbs }
            }
    }

    private suspend fun createDish(description: String = "", imageBytes: ByteArray?) {
        createDishUseCase.execute(description, imageBytes)
            .collect { status -> _createDishResult.emit(status) }
    }

    fun getCalorieIntake(): Int {
        return settingsManager.get(SettingsManager.DAILY_CALORIE_INTAKE_KEY, 2000)
    }

    fun getProteinIntake(): Int {
        return settingsManager.get(SettingsManager.DAILY_PROTEIN_INTAKE_KEY, 100)
    }

    fun getFatIntake(): Int {
        return settingsManager.get(SettingsManager.DAILY_FAT_INTAKE_KEY, 100)
    }

    fun getCarbsIntake(): Int {
        return settingsManager.get(SettingsManager.DAILY_CARBS_INTAKE_KEY, 100)
    }
}