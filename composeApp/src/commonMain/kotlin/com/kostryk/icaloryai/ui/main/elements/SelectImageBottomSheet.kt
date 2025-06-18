package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kostryk.icaloryai.theme.isDarkTheme
import com.kostryk.icaloryai.ui.profile.elements.Divider
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.camera
import icaloryai.composeapp.generated.resources.galley
import icaloryai.composeapp.generated.resources.ic_camera_square
import icaloryai.composeapp.generated.resources.ic_gallery_circle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectImageBottomSheet(
    sheetState: SheetState,
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onTakePhotoActionSelected: () -> Unit,
    onPickGalleryActionSelected: () -> Unit,
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.wrapContentHeight(),
            sheetState = sheetState,
            onDismissRequest = { onDismissRequest() },
        ) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onTakePhotoActionSelected()
                        onDismissRequest()
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_camera_square),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(Res.string.camera),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Spacer(Modifier.height(24.dp))

                Divider()

                Spacer(Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onPickGalleryActionSelected()
                        onDismissRequest()
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_gallery_circle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(Res.string.galley),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}