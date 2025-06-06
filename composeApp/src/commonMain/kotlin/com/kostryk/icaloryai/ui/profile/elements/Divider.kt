package com.kostryk.icaloryai.ui.profile.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = Color.LightGray,
        thickness = 1.dp
    )
}