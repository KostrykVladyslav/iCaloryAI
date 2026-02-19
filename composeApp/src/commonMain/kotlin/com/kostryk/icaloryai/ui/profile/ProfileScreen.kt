package com.kostryk.icaloryai.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.ui.profile.dialog.ThemeSelectorBottomSheet
import com.kostryk.icaloryai.ui.profile.elements.Divider
import com.kostryk.icaloryai.ui.profile.elements.ProfileInfoRow
import com.kostryk.icaloryai.ui.profile.elements.ProfileMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun ProfileScreen(onBack: () -> Unit = {}) {
    val viewModel = koinViewModel<ProfileViewModel>()
    var showThemeSelector by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ProfileInfoRow("Gender", "Male")
                    ProfileInfoRow("Age", "23")
                    ProfileInfoRow("Height", "168 cm")
                    ProfileInfoRow("Current Weight", "86 kg")
                }
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = "Customization",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileMenuItem(title = "Personal details", onClick = { })
                    Divider()
                    ProfileMenuItem(
                        title = "Selected theme",
                        subtitle = when (viewModel.getThemeType()) {
                            ThemeType.SYSTEM -> "System Default"
                            ThemeType.DARK -> "Dark"
                            ThemeType.LIGHT -> "Light"
                        },
                        onClick = { showThemeSelector = true }
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = "Support",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileMenuItem(title = "Support", onClick = { })
                    Divider()
                    ProfileMenuItem(title = "Terms of Use", onClick = { })
                    Divider()
                    ProfileMenuItem(title = "Privacy Policy", onClick = { })
                }
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "version: 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 42.dp, bottom = 12.dp)
            )
        }
    }

    if (showThemeSelector) {
        ThemeSelectorBottomSheet(
            currentThemeType = viewModel.getThemeType(),
            onDismiss = { showThemeSelector = false },
            onThemeSelected = { viewModel.setThemeType(it) }
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}