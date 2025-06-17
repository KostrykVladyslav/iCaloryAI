package com.kostryk.icaloryai.ui.main.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kostryk.icaloryai.graph.NavigationRoute
import icaloryai.composeapp.generated.resources.Res
import icaloryai.composeapp.generated.resources.app_name
import icaloryai.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenToolbar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
        },
        actions = {
            Icon(
                painter = painterResource(Res.drawable.ic_profile),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
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
}