package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kostryk.icaloryai.arch.manager.camera.rememberCameraManager
import com.kostryk.icaloryai.arch.manager.gallery.rememberGalleryManager
import com.kostryk.icaloryai.arch.manager.permissions.PermissionCallback
import com.kostryk.icaloryai.arch.manager.permissions.PermissionStatus
import com.kostryk.icaloryai.arch.manager.permissions.PermissionType
import com.kostryk.icaloryai.arch.manager.permissions.createPermissionsManager
import com.kostryk.icaloryai.ui.main.MainViewModel
import com.kostryk.icaloryai.ui.main.dialog.AlertMessageDialog
import com.kostryk.icaloryai.ui.main.dialog.SelectImageBottomSheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawImagePicker(
    showBottomSheet: Boolean,
    viewModel: MainViewModel,
    onBottomSheetDismissed: () -> Unit
) {
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
                        PermissionType.CAMERA -> { launchCamera = false }
                        PermissionType.GALLERY -> { launchGallery = false}
                    }
                }

                else -> showAlertDialog = true
            }
        }
    })

    SelectImageBottomSheet(
        sheetState = rememberModalBottomSheetState(true),
        showBottomSheet = showBottomSheet,
        onDismissRequest = onBottomSheetDismissed,
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
                launchSetting = true
                showAlertDialog = false
                launchCamera = false
                launchGallery = false
            },
            onNegativeClick = {
                showAlertDialog = false
                launchCamera = false
                launchGallery = false
            })
    }

    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }
}