package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MacroProgressBar(name: String, value: Int, max: Int) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Text(
                text = "$value/$max${if (name == "Protein" || name == "Fat" || name == "Carbs") "g" else ""}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(Color(0xFFF0F0F0), RoundedCornerShape(3.dp))
        ) {
            Box(
                Modifier
                    .fillMaxWidth(fraction = if (max == 0) 0f else value / max.toFloat())
                    .height(6.dp)
                    .background(Color.LightGray, RoundedCornerShape(3.dp))
            )
        }
    }
}