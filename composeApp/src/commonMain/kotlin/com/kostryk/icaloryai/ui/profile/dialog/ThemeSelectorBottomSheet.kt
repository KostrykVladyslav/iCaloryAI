package com.kostryk.icaloryai.ui.profile.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.domain.enums.theme.ThemeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorBottomSheet(
    currentThemeType: ThemeType,
    onDismiss: () -> Unit,
    onThemeSelected: (ThemeType) -> Unit
) {
    val radioOptions = listOf("System", "Dark", "Light")
    ModalBottomSheet(
        modifier = Modifier.wrapContentHeight(),
        onDismissRequest = { onDismiss() },
    ) {
        Column(Modifier.fillMaxWidth()) {
            Spacer(Modifier.height(24.dp))

            Text(
                text = "Select theme",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(12.dp))

            radioOptions.forEachIndexed { index, text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = ThemeType.entries[index] == currentThemeType,
                            onClick = {
                                onThemeSelected(ThemeType.entries[index])
                                onDismiss()
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = ThemeType.entries[index] == currentThemeType,
                        colors = RadioButtonColors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.onPrimary,
                            disabledSelectedColor = MaterialTheme.colorScheme.primary,
                            disabledUnselectedColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = null
                    )
                    Text(
                        text = text,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}