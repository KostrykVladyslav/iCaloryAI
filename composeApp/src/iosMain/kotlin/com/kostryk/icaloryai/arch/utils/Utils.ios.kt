package com.kostryk.icaloryai.arch.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.get
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorSpace
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import platform.CoreFoundation.CFDataGetBytePtr
import platform.CoreFoundation.CFDataGetLength
import platform.CoreGraphics.CGDataProviderCopyData
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageGetAlphaInfo
import platform.CoreGraphics.CGImageGetBytesPerRow
import platform.CoreGraphics.CGImageGetDataProvider
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.CoreGraphics.CGImageRelease
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage
import platform.UIKit.*
import platform.CoreGraphics.CGRectMake
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.refTo
import platform.CoreGraphics.CGSizeMake
import platform.CoreGraphics.CGSizeMake

@OptIn(ExperimentalForeignApi::class)
actual fun byteArrayToImageBitmapWithResize(bytes: ByteArray): ImageBitmap {
//    val uiImage = UIImage(byteArrayToNSData(bytes))
//
//    val newWidth = uiImage.size.useContents { width } / 5.0
//    val newHeight = uiImage.size.useContents { height } / 5.0
//
//    UIGraphicsBeginImageContextWithOptions(
//        size = CGSizeMake(newWidth, newHeight),
//        false,
//        1.0
//    )
//    uiImage.drawInRect(CGRectMake(0.0, 0.0, newWidth, newHeight))
//    val resizedImage = UIGraphicsGetImageFromCurrentImageContext()
//    UIGraphicsEndImageContext()
//
//    val jpegData = resizedImage?.JPEGRepresentation(0.8) ?: return ImageBitmap(1, 1)
//    val resizedBytes = ByteArray(jpegData.length.toInt())
//    jpegData.getBytes(resizedBytes.refTo(0), jpegData.length)

    return byteArrayToImageBitmap(bytes)
}

actual fun byteArrayToImageBitmap(bytes: ByteArray): ImageBitmap {
    val uiImage = UIImage(byteArrayToNSData(bytes)).toSkiaImage()
    return uiImage?.toComposeImageBitmap() ?: ImageBitmap(1, 1)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun byteArrayToNSData(bytes: ByteArray): NSData =
    bytes.usePinned { pinned ->
        NSData.create(
            bytes = pinned.addressOf(0),
            length = bytes.size.toULong()
        )
    }

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.toSkiaImage(resize: Boolean = false): Image? {
    val imageRef = this.CGImage ?: return null

    val width = CGImageGetWidth(imageRef).toInt()
    val height = CGImageGetHeight(imageRef).toInt()

    val bytesPerRow = CGImageGetBytesPerRow(imageRef)
    val data = CGDataProviderCopyData(CGImageGetDataProvider(imageRef))
    val bytePointer = CFDataGetBytePtr(data)
    val length = CFDataGetLength(data)

    val alphaType = when (CGImageGetAlphaInfo(imageRef)) {
        CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst,
        CGImageAlphaInfo.kCGImageAlphaPremultipliedLast -> ColorAlphaType.PREMUL

        CGImageAlphaInfo.kCGImageAlphaFirst,
        CGImageAlphaInfo.kCGImageAlphaLast -> ColorAlphaType.UNPREMUL

        CGImageAlphaInfo.kCGImageAlphaNone,
        CGImageAlphaInfo.kCGImageAlphaNoneSkipFirst,
        CGImageAlphaInfo.kCGImageAlphaNoneSkipLast -> ColorAlphaType.OPAQUE

        else -> ColorAlphaType.UNKNOWN
    }

    val byteArray = ByteArray(length.toInt()) { index ->
        bytePointer!![index].toByte()
    }

    CGImageRelease(imageRef)

    val skiaColorSpace = ColorSpace.sRGB
    val colorType = ColorType.RGBA_8888

    for (i in byteArray.indices step 4) {
        val r = byteArray[i]
        val g = byteArray[i + 1]
        val b = byteArray[i + 2]
        val a = byteArray[i + 3]

        byteArray[i] = b
        byteArray[i + 2] = r
    }

    return Image.makeRaster(
        imageInfo = ImageInfo(
            width = if (resize) width / 5 else width,
            height = if (resize) height / 5 else height,
            colorType = colorType,
            alphaType = alphaType,
            colorSpace = skiaColorSpace
        ),
        bytes = byteArray,
        rowBytes = bytesPerRow.toInt(),
    )
}