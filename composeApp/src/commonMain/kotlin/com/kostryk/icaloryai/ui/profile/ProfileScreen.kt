package com.kostryk.icaloryai.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.theme.isDarkTheme
import com.kostryk.icaloryai.ui.profile.dialog.ThemeSelectorBottomSheet
import com.kostryk.icaloryai.ui.profile.elements.Divider
import com.kostryk.icaloryai.ui.profile.elements.ProfileInfoRow
import com.kostryk.icaloryai.ui.profile.elements.ProfileMenuItem
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel = koinViewModel<ProfileViewModel>()
    var showThemeSelector by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(34.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = if (isDarkTheme()) Color.DarkGray else Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
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
            modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
        )
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = if (isDarkTheme()) Color.DarkGray else Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
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
                    onClick = {
                        showThemeSelector = true
                    })
            }
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Support",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
        )
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    if (isDarkTheme()) Color.DarkGray else Color.White,
                    RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
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
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 42.dp, bottom = 12.dp)
        )
    }

    if (showThemeSelector) {
        ThemeSelectorBottomSheet(
            currentThemeType = viewModel.getThemeType(),
            onDismiss = { showThemeSelector = false },
            onThemeSelected = { viewModel.setThemeType(it) }
        )
    }
}