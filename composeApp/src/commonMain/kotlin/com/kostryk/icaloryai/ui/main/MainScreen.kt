package com.kostryk.icaloryai.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.domain.entities.result.CreateDishStatusEntity
import com.kostryk.icaloryai.ui.main.dialog.AlertMessageDialog
import com.kostryk.icaloryai.ui.main.elements.CalendarWeekSection
import com.kostryk.icaloryai.ui.main.elements.CaloriesAndMacrosSection
import com.kostryk.icaloryai.ui.main.elements.DishLoadingSection
import com.kostryk.icaloryai.ui.main.elements.DishSection
import com.kostryk.icaloryai.ui.main.elements.DrawImagePicker
import com.kostryk.icaloryai.ui.main.elements.MainScreenToolbar
import com.kostryk.icaloryai.ui.main.elements.NoDishesSection
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun MainScreen(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToDish: (Long) -> Unit = {}
) {
    val viewModel = koinViewModel<MainViewModel>()

    val calendarWeeks by viewModel.calendarWeeks.collectAsState()
    val dishesWithImages by viewModel.dishesWithImages.collectAsState()
    val macroState by viewModel.macroState.collectAsState()
    val createDishResult by viewModel.createDishResult.collectAsState()

    var showAlertData: Pair<String, String>? by remember { mutableStateOf(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(
        pageCount = { calendarWeeks.size },
        initialPage = viewModel.initialWeekIndex
    )

    val isToday by remember {
        derivedStateOf {
            val week = calendarWeeks.getOrNull(pagerState.currentPage)
            week?.days?.getOrNull(week.selectedDayIndex)?.fullDate == viewModel.getCurrentDate()
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.onWeekSwiped(it)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item(key = "toolbar") { MainScreenToolbar(onProfileClick = onNavigateToProfile) }

            item(key = "toolbar_spacer") { Spacer(Modifier.height(16.dp)) }

            item(key = "calendar") {
                HorizontalPager(
                    state = pagerState,
                    reverseLayout = true
                ) { page ->
                    CalendarWeekSection(
                        week = calendarWeeks[page]
                    ) { dayIndex ->
                        viewModel.onDaySelected(page, dayIndex)
                    }
                }
            }

            item(key = "calendar_spacer") {
                Spacer(Modifier.height(16.dp))
            }

            item(key = "macros") {
                CaloriesAndMacrosSection(
                    calories = macroState.calories to viewModel.getCalorieIntake(),
                    protein = macroState.protein to viewModel.getProteinIntake(),
                    fat = macroState.fat to viewModel.getFatIntake(),
                    carbs = macroState.carbs to viewModel.getCarbsIntake()
                )
            }

            item(key = "recently_spacer") {
                Spacer(Modifier.height(32.dp))
            }

            item(key = "recently_title") {
                Text(
                    text = "Recently",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (dishesWithImages.isEmpty()) {
                item(key = "no_dishes") { NoDishesSection(Modifier.animateItem()) }
            }

            items(
                items = dishesWithImages,
                key = { it.second.id }
            ) { (image, dish) ->
                DishSection(
                    image = image,
                    dish = dish,
                    onDishSelected = { onNavigateToDish(it.id) },
                    modifier = Modifier.animateItem()
                )
                Spacer(Modifier.height(12.dp))
            }

            item(key = "loading") {
                when (val result = createDishResult) {
                    is CreateDishStatusEntity.Loading -> {
                        if (dishesWithImages.isEmpty()) {
                            Spacer(Modifier.height(12.dp))
                        }
                        DishLoadingSection(modifier = Modifier.animateItem())
                    }

                    is CreateDishStatusEntity.Error -> showAlertData = "Error" to result.message
                    else -> {}
                }
            }

            item(key = "bottom_spacer") {
                Spacer(Modifier.height(96.dp))
            }
        }

        AnimatedVisibility(
            visible = isToday,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(26.dp)
                .wrapContentSize()
        ) {
            Box(
                Modifier.shadow(4.dp, RoundedCornerShape(20.dp))
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    ).clickable(
                        interactionSource = null,
                        indication = null
                    ) { showBottomSheet = !showBottomSheet }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_plus),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(width = 70.dp, height = 40.dp)
                        .padding(8.dp)
                )
            }
        }

        DrawImagePicker(showBottomSheet, viewModel) { showBottomSheet = false }
    }

    if (showAlertData != null) {
        AlertMessageDialog(
            title = showAlertData?.first.orEmpty(),
            message = showAlertData?.second.orEmpty(),
            positiveButtonText = "Ok",
            negativeButtonText = "Cancel",
            onPositiveClick = { showAlertData = null },
            onNegativeClick = { showAlertData = null })
    }
}