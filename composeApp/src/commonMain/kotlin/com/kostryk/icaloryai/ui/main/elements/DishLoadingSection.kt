package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.theme.isDarkTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun DishLoadingSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isDarkTheme()) Color.DarkGray else Color.White,
                RoundedCornerShape(16.dp)
            )
            .shimmer()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = if (isDarkTheme()) Color.Gray else Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .height(18.dp)
                        .fillMaxWidth(0.6f)
                        .background(
                            color = if (isDarkTheme()) Color.Gray else Color.LightGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                Spacer(Modifier.height(8.dp))
                Row {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width(40.dp)
                                .background(
                                    color = if (isDarkTheme()) Color.Gray else Color.LightGray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(32.dp)
                    .background(
                        color = if (isDarkTheme()) Color.Gray else Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}

