package com.kostryk.icaloryai.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kostryk.icaloryai.arch.manager.camera.rememberCameraManager
import com.kostryk.icaloryai.arch.manager.gallery.rememberGalleryManager
import com.kostryk.icaloryai.arch.manager.permissions.PermissionCallback
import com.kostryk.icaloryai.arch.manager.permissions.PermissionStatus
import com.kostryk.icaloryai.arch.manager.permissions.PermissionType
import com.kostryk.icaloryai.arch.manager.permissions.createPermissionsManager
import com.kostryk.icaloryai.graph.NavigationRoute
import com.kostryk.icaloryai.ui.main.dialog.AlertMessageDialog
import com.kostryk.icaloryai.ui.main.elements.CalendarWeekSection
import com.kostryk.icaloryai.ui.main.elements.CaloriesAndMacrosSection
import com.kostryk.icaloryai.ui.main.elements.SelectImageBottomSheet
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.app_name
import icaloryai.composeapp.generated.resources.ic_plus
import icaloryai.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel = koinViewModel<MainViewModel>()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = androidx.compose.material3.MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_profile),
                        contentDescription = null,
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier
                            .size(height = 44.dp, width = 44.dp)
                            .padding(8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(false)
                            ) {
                                navController.navigate(NavigationRoute.Profile.route)
                            },
                    )
                }
            )
        },
        floatingActionButton = {
            Box(
                Modifier.background(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    shape = RoundedCornerShape(20.dp)
                ).clickable(
                    interactionSource = null,
                    indication = null
                ) { showBottomSheet = !showBottomSheet }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_plus),
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) Color.Black else Color.White,
                    modifier = Modifier
                        .size(width = 70.dp, height = 40.dp)
                        .padding(8.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(it)
        ) {
            Spacer(Modifier.height(16.dp))
            CalendarWeekSection(
                daysAndDates = listOf(
                    "M" to 2,
                    "T" to 3,
                    "W" to 4,
                    "T" to 5,
                    "F" to 6,
                    "S" to 7,
                    "S" to 8
                ),
                selectedIndex = 4
            )
            Spacer(Modifier.height(16.dp))
            CaloriesAndMacrosSection(
                caloriesLeft = 1750,
                protein = 0 to 131,
                fat = 0 to 58,
                carbs = 0 to 175
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = "Recently",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSystemInDarkTheme()) Color.DarkGray else Color.White,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No meals yet!",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Grab something tasty and log it here",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    val cameraManager = rememberCameraManager { viewModel.handleCameraResult(it) }
    val galleryManager = rememberGalleryManager { viewModel.handleGalleryResult(it) }

    var showAlertDialog by remember { mutableStateOf(false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> cameraManager.launch()
                        PermissionType.GALLERY -> galleryManager.launch()
                    }
                }

                else -> showAlertDialog = true
            }
        }
    })

    SelectImageBottomSheet(
        sheetState = rememberModalBottomSheetState(true),
        showBottomSheet = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        onTakePhotoActionSelected = {
            launchCamera = true
        },
        onPickGalleryActionSelected = {
            launchGallery = true
        }
    )

    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
            launchCamera = false
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
    }

    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
            launchGallery = false
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
    }

    if (showAlertDialog) {
        AlertMessageDialog(
            title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                showAlertDialog = false
                launchSetting = true
            },
            onNegativeClick = {
                showAlertDialog = false
            })
    }

    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }
}