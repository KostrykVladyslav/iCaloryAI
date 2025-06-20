package com.kostryk.icaloryai.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
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
fun MainScreen(navController: NavController) {
    val viewModel = koinViewModel<MainViewModel>()

    val selectedDate by viewModel.selectedDate.collectAsState(viewModel.getCurrentDate())

    val dishesWithImages = viewModel.dishesWithImages.collectAsState(listOf())
    var showAlertData: Pair<String, String>? by remember { mutableStateOf(null) }

    val weeks by viewModel.weeks.collectAsState(emptyList())
    val selectedWeekIndex by viewModel.selectedWeekIndex.collectAsState(null)

    val pagerState = rememberPagerState(
        pageCount = { weeks.size },
        initialPage = selectedWeekIndex ?: 0
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.onWeekChanged(it)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item { MainScreenToolbar(navController) }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            HorizontalPager(
                state = pagerState,
                reverseLayout = true
            ) { page ->
                val currentWeek = weeks[page]
                CalendarWeekSection(
                    daysAndDates = listOf(
                        "Mon" to currentWeek[0].split("-").last().toInt(),
                        "Tue" to currentWeek[1].split("-").last().toInt(),
                        "Wed" to currentWeek[2].split("-").last().toInt(),
                        "Tue" to currentWeek[3].split("-").last().toInt(),
                        "Fri" to currentWeek[4].split("-").last().toInt(),
                        "Sat" to currentWeek[5].split("-").last().toInt(),
                        "Sun" to currentWeek[6].split("-").last().toInt()
                    ),
                    selectedIndex = currentWeek.indexOf(selectedDate).takeIf { it >= 0 }
                        ?: 0
                ) {
                    viewModel.onDateSelected(currentWeek[it])
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            val caloriesSpend = viewModel.caloriesSpend.collectAsState(0)
            val proteinSpend = viewModel.proteinSpend.collectAsState(0)
            val fatSpend = viewModel.farSpend.collectAsState(0)
            val carbsSpend = viewModel.carbsSpend.collectAsState(0)

            CaloriesAndMacrosSection(
                calories = caloriesSpend.value to viewModel.getCalorieIntake(),
                protein = proteinSpend.value to viewModel.getProteinIntake(),
                fat = fatSpend.value to viewModel.getFatIntake(),
                carbs = carbsSpend.value to viewModel.getCarbsIntake()
            )
        }

        item {
            Spacer(Modifier.height(32.dp))
        }

        item {
            Text(
                text = "Recently",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (dishesWithImages.value.isEmpty()) {
            item { NoDishesSection(Modifier.animateItem()) }
        }

        val images = dishesWithImages.value.map { it.first }
        val dishes = dishesWithImages.value.map { it.second }
        dishes.forEachIndexed { index, dish ->
            item {
                DishSection(
                    image = images[index],
                    dish = dish,
                    onDishSelected = {

                    },
                    modifier = Modifier.animateItem()
                )
                Spacer(Modifier.height(12.dp))
            }
        }

        item {
            val createDishResult = viewModel.createDishResult.collectAsState(null)

            when (val result = createDishResult.value) {
                is CreateDishStatusEntity.Loading -> {
                    if (dishesWithImages.value.isEmpty()) {
                        Spacer(Modifier.height(12.dp))
                    }
                    DishLoadingSection(modifier = Modifier.animateItem())
                }

                is CreateDishStatusEntity.Error -> showAlertData = "Error" to result.message
                else -> {}
            }
        }

        item {
            Spacer(Modifier.height(96.dp))
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(26.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = selectedDate == viewModel.getCurrentDate(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
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
    }

    DrawImagePicker(showBottomSheet, viewModel) { showBottomSheet = false }

    if (showAlertData != null) {
        AlertMessageDialog(
            title = showAlertData?.first.orEmpty(),
            message = showAlertData?.first.orEmpty(),
            positiveButtonText = "Ok",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                showAlertData = null
            },
            onNegativeClick = {
                showAlertData = null
            })
    }
}