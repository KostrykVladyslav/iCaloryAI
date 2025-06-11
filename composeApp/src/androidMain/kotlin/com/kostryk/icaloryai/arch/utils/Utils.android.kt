package com.kostryk.icaloryai.arch.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageBitmap

actual fun byteArrayToImageBitmap(bytes: ByteArray): ImageBitmap {
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return bitmap.asImageBitmap()
}