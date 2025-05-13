package com.kostryk.icaloryai.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Text(text = "this is calory ai")
    }
}

@Preview
@Composable
private fun MainScreenPreview() = MainScreen()