package com.kostryk.icaloryai.arch.manager.gallery

import androidx.compose.runtime.Composable
import com.kostryk.icaloryai.arch.manager.file.SharedImage

@Composable
expect fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class GalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}