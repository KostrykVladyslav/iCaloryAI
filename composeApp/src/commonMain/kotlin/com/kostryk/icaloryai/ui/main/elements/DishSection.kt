package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.domain.entities.dish.DishEntity
import com.kostryk.icaloryai.theme.isDarkTheme
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.ic_food_balck
import icaloryai.composeapp.generated.resources.ic_food_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun DishSection(
    image: ImageBitmap? = null,
    dish: DishEntity,
    onDishSelected: (DishEntity) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isDarkTheme()) Color.DarkGray else Color.White,
                RoundedCornerShape(16.dp)
            )
            .clickable { onDishSelected(dish) }
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = if (isDarkTheme()) Color.Gray else Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                val painter = image?.let { BitmapPainter(it) } ?: if (isDarkTheme()) {
                    painterResource(Res.drawable.ic_food_white)
                } else painterResource(Res.drawable.ic_food_balck)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${dish.calories} Calories",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "P ${dish.protein} g",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE57373)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "F ${dish.fats} g",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFFB74D)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "C ${dish.carbs} g",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64B5F6)
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = dish.displayTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}