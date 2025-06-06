package com.kostryk.icaloryai.ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.graph.NavigationRoute
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.app_name
import icaloryai.composeapp.generated.resources.camera
import icaloryai.composeapp.generated.resources.galley
import icaloryai.composeapp.generated.resources.ic_camera_square
import icaloryai.composeapp.generated.resources.ic_gallery_circle
import icaloryai.composeapp.generated.resources.ic_plus
import icaloryai.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(true)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
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
        }, floatingActionButton = {
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
        }, floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(it)
        ) {


        }
    }
    SelectImageBottomSheet(
        sheetState = sheetState,
        showBottomSheet = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        onTakePhotoActionSelected = {},
        onPickGalleryActionSelected = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectImageBottomSheet(
    sheetState: SheetState,
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onTakePhotoActionSelected: (String) -> Unit,
    onPickGalleryActionSelected: (String) -> Unit,
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
                        onDismissRequest()
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_camera_square),
                        contentDescription = null,
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(Res.string.camera),
                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
                    )
                }

                Spacer(Modifier.height(24.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onDismissRequest()
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_gallery_circle),
                        contentDescription = null,
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(Res.string.galley),
                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() = MainScreen(rememberNavController())