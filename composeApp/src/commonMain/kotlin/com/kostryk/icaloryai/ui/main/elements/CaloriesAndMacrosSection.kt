package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.theme.CarbsColor
import com.kostryk.icaloryai.theme.FatColor
import com.kostryk.icaloryai.theme.ProteinColor
import com.kostryk.icaloryai.theme.isDarkTheme

@Composable
fun CaloriesAndMacrosSection(
    calories: Pair<Int, Int>,
    protein: Pair<Int, Int>,
    fat: Pair<Int, Int>,
    carbs: Pair<Int, Int>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isDarkTheme()) Color.DarkGray else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressBar(
                    percentage = calories.first / calories.second.toFloat(),
                    calories = calories.second - calories.first
                )
            }
            Spacer(Modifier.width(24.dp))
            Column(modifier = Modifier.weight(1f)) {
                MacroProgressBar("Protein", ProteinColor, protein.first, protein.second)
                Spacer(Modifier.height(8.dp))
                MacroProgressBar("Fat", FatColor, fat.first, fat.second)
                Spacer(Modifier.height(8.dp))
                MacroProgressBar("Carbs", CarbsColor, calories.first, carbs.second)
            }
        }
    }
}