package com.kostryk.icaloryai.ui.main

import androidx.compose.ui.graphics.ImageBitmap
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalendarDay(
    val label: String,
    val dayOfMonth: Int,
    val fullDate: String,
    val isFuture: Boolean = false
)

data class CalendarWeek(
    val days: List<CalendarDay>,
    val selectedDayIndex: Int
)

data class MacroState(
    val calories: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0
)

class MainViewModel(
    private val getAllDishesUseCase: GetAllDishesUseCase,
    private val createDishUseCase: CreateDishUseCase,
    private val dateManager: DateTimeManager,
    private val settingsManager: SettingsManager
) : BaseViewModel() {

    private val _dishesWithImages =
        MutableStateFlow<List<Pair<ImageBitmap?, DishEntity>>>(emptyList())
    val dishesWithImages: StateFlow<List<Pair<ImageBitmap?, DishEntity>>> =
        _dishesWithImages.asStateFlow()

    private val _createDishResult = MutableStateFlow<CreateDishStatusEntity?>(null)
    val createDishResult: StateFlow<CreateDishStatusEntity?> = _createDishResult.asStateFlow()

    private val _macroState = MutableStateFlow(MacroState())
    val macroState: StateFlow<MacroState> = _macroState.asStateFlow()

    private val today: String = getCurrentDate()
    private val rawWeeks: List<List<String>> = dateManager.createLastWeeks()

    private val _calendarWeeks = MutableStateFlow(buildCalendarWeeks())
    val calendarWeeks: StateFlow<List<CalendarWeek>> = _calendarWeeks.asStateFlow()

    val initialWeekIndex: Int = 0

    private var loadDishesJob: Job? = null

    init {
        loadDishesByDate(today)
    }

    fun onDaySelected(weekIndex: Int, dayIndex: Int) {
        val weeks = _calendarWeeks.value.toMutableList()
        val week = weeks[weekIndex]
        val day = week.days[dayIndex]
        if (day.isFuture) return
        weeks[weekIndex] = week.copy(selectedDayIndex = dayIndex)
        _calendarWeeks.value = weeks
        loadDishesByDate(day.fullDate)
    }

    fun onWeekSwiped(weekIndex: Int) {
        val week = _calendarWeeks.value.getOrNull(weekIndex) ?: return
        val selectedDay = week.days[week.selectedDayIndex]
        loadDishesByDate(selectedDay.fullDate)
    }

    fun getSelectedDate(weekIndex: Int): String {
        val week = _calendarWeeks.value.getOrNull(weekIndex) ?: return today
        return week.days[week.selectedDayIndex].fullDate
    }

    fun getCurrentDate(format: String = DateTimeManager.DEFAULT_DATE_FORMAT): String {
        return dateManager.getCurrentDateTime(format)
    }

    fun handleCameraResult(image: SharedImage?) {
        _createDishResult.value = CreateDishStatusEntity.Loading
        launch(context = Dispatchers.IO) { createDish(imageBytes = image?.toByteArray()) }
    }

    fun handleGalleryResult(image: SharedImage?) {
        _createDishResult.value = CreateDishStatusEntity.Loading
        launch(context = Dispatchers.IO) { createDish(imageBytes = image?.toByteArray()) }
    }

    private fun loadDishesByDate(date: String) {
        loadDishesJob?.cancel()
        loadDishesJob = launch(context = Dispatchers.IO) {
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
                _macroState.value = MacroState(
                    calories = sumOf { it.second.calories },
                    protein = sumOf { it.second.protein },
                    fat = sumOf { it.second.fats },
                    carbs = sumOf { it.second.carbs }
                )
            }
    }

    private suspend fun createDish(description: String = "", imageBytes: ByteArray?) {
        createDishUseCase.execute(description, imageBytes)
            .collect { status -> _createDishResult.emit(status) }
    }

    fun getCalorieIntake(): Int =
        settingsManager.get(SettingsManager.DAILY_CALORIE_INTAKE_KEY, 2000)

    fun getProteinIntake(): Int =
        settingsManager.get(SettingsManager.DAILY_PROTEIN_INTAKE_KEY, 100)

    fun getFatIntake(): Int =
        settingsManager.get(SettingsManager.DAILY_FAT_INTAKE_KEY, 100)

    fun getCarbsIntake(): Int =
        settingsManager.get(SettingsManager.DAILY_CARBS_INTAKE_KEY, 100)

    private fun buildCalendarWeeks(): List<CalendarWeek> {
        return rawWeeks.mapIndexed { weekIndex, dates ->
            val days = dates.map { it.toCalendarDay(today) }
            val selectedDayIndex = if (weekIndex == 0) {
                days.indexOfFirst { it.fullDate == today }.coerceAtLeast(0)
            } else {
                days.lastIndex
            }
            CalendarWeek(days = days, selectedDayIndex = selectedDayIndex)
        }
    }

    companion object {
        private val DAY_LABELS = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        private fun String.toCalendarDay(today: String): CalendarDay {
            val parts = this.split("-")
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val dayOfMonth = parts[2].toInt()
            val dayOfWeekIndex = dayOfWeek(year, month, dayOfMonth)
            return CalendarDay(
                label = DAY_LABELS[dayOfWeekIndex],
                dayOfMonth = dayOfMonth,
                fullDate = this,
                isFuture = this > today
            )
        }

        private fun dayOfWeek(year: Int, month: Int, day: Int): Int {
            val t = intArrayOf(0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
            val y = if (month < 3) year - 1 else year
            val dow = (y + y / 4 - y / 100 + y / 400 + t[month - 1] + day) % 7
            return (dow + 6) % 7
        }
    }
}