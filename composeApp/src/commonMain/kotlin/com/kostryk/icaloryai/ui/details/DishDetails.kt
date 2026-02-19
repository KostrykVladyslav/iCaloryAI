package com.kostryk.icaloryai.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.ic_food_balck
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DishDetailsScreen(dishId: String, onBack: () -> Unit = {}) {
    val viewModel = koinViewModel<DishDetailsViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(dishId) {
        viewModel.loadDish(dishId)
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onBack()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // ── Image Header ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            val dish = state.dish
            val image = state.image
            if (image != null) {
                Image(
                    painter = BitmapPainter(image),
                    contentDescription = dish?.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_food_balck),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            // Back button overlay
            IconButton(
                onClick = { onBack() },
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 24.dp)
                    .size(36.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Time badge
            if (state.dish?.displayTime?.isNotEmpty() == true) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = state.dish?.displayTime.orEmpty(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        // ── Content Card ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Dish name
            Text(
                text = state.dish?.name.orEmpty(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(24.dp))

            // Calories
            Text(
                text = "Calories",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            MacroTextField(
                value = state.editCalories,
                onValueChange = viewModel::onCaloriesChanged,
                suffix = "kcal"
            )

            Spacer(Modifier.height(20.dp))

            // Protein & Fat row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    MacroLabel(text = "Protein", color = Color(0xFFE57373))
                    Spacer(Modifier.height(4.dp))
                    MacroTextField(
                        value = state.editProtein,
                        onValueChange = viewModel::onProteinChanged,
                        suffix = "g",
                        badgeColor = Color(0xFFE57373),
                        badgeLetter = "P"
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    MacroLabel(text = "Fat", color = Color(0xFFFFB74D))
                    Spacer(Modifier.height(4.dp))
                    MacroTextField(
                        value = state.editFat,
                        onValueChange = viewModel::onFatChanged,
                        suffix = "g",
                        badgeColor = Color(0xFFFFB74D),
                        badgeLetter = "F"
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Carbs
            Column(modifier = Modifier.fillMaxWidth(0.48f)) {
                MacroLabel(text = "Carbs", color = Color(0xFF64B5F6))
                Spacer(Modifier.height(4.dp))
                MacroTextField(
                    value = state.editCarbs,
                    onValueChange = viewModel::onCarbsChanged,
                    suffix = "g",
                    badgeColor = Color(0xFF64B5F6),
                    badgeLetter = "C"
                )
            }

            Spacer(Modifier.height(32.dp))

            // Save button
            Button(
                onClick = { viewModel.saveDish() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "✨ Save changes",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MacroLabel(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = color
    )
}

@Composable
private fun MacroTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suffix: String,
    badgeColor: Color? = null,
    badgeLetter: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        textStyle = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ),
        leadingIcon = if (badgeColor != null && badgeLetter != null) {
            {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(badgeColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = badgeLetter,
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        } else null,
        suffix = {
            Text(
                text = suffix,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}