package com.kostryk.icaloryai.arch.manager.camera

import androidx.compose.runtime.Composable
import com.kostryk.icaloryai.arch.manager.file.SharedImage

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}