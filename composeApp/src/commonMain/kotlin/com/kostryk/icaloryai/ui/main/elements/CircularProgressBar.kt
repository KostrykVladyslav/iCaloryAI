package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.theme.isDarkTheme

@Composable
fun CircularProgressBar(percentage: Float, calories: Int) {
    Box(contentAlignment = Alignment.Center) {
        androidx.compose.foundation.Canvas(modifier = Modifier.size(120.dp)) {
            drawArc(
                color = Color.LightGray,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 22f,
                    cap = StrokeCap.Round
                )
            )
            drawArc(
                color = Color(0xFFFF7600), // Primary color
                startAngle = 135f,
                sweepAngle = 270f * percentage,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 22f,
                    cap = StrokeCap.Round
                )
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = calories.toString(),
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = if (isDarkTheme()) Color.White else Color.Black
            )
            Text(
                text = "Calories left",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = if (isDarkTheme()) Color.White else Color.Black
            )
        }
    }
}