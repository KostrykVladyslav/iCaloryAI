package com.kostryk.icaloryai.preview.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.ui.main.MainScreen

@Preview
@Composable
fun mainScreenPreview() {
    MainScreen(rememberNavController())
}