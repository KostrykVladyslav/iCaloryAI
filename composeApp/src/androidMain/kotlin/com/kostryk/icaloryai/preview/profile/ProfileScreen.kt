package com.kostryk.icaloryai.preview.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.ui.profile.ProfileScreen


@Composable
@Preview
private fun previewProfileScreen() {
    ProfileScreen(rememberNavController())
}