package com.kostryk.icaloryai.arch.utils

import androidx.compose.ui.graphics.ImageBitmap

expect fun byteArrayToImageBitmap(bytes: ByteArray): ImageBitmap

expect fun byteArrayToImageBitmapWithResize(bytes: ByteArray): ImageBitmap