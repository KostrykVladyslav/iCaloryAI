package com.kostryk.icaloryai.preview.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.graph.NavigationRoute
import com.kostryk.icaloryai.ui.main.MainScreen
import com.kostryk.icaloryai.ui.profile.ProfileScreen

@Preview
@Composable
fun mainScreenPreview() {
   ProfileScreen(rememberNavController())
}