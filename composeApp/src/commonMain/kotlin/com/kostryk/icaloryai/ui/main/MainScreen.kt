package com.kostryk.icaloryai.ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.graph.NavigationRoute
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.ic_plus
import icaloryai.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        floatingActionButton = {
            Box(
                Modifier.background(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_plus),
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) Color.Black else Color.White,
                    modifier = Modifier.size(width = 70.dp, height = 40.dp)
                        .padding(8.dp)
                        .clickable { navController.navigate(NavigationRoute.Profile) })
            }
        },
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "iCaloryAI",
                    modifier = Modifier
                        .weight(1f),
                    style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.ic_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.navigate(NavigationRoute.Profile) },
                )
            }
        },
        bottomBar = {},
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Hello, World!"
            )
        }

    }
}

@Preview
@Composable
private fun MainScreenPreview() = MainScreen(rememberNavController())