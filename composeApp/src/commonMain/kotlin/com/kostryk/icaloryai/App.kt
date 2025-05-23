package com.kostryk.icaloryai

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.graph.AppNavGraph
import com.kostryk.icaloryai.theme.iCaloryAITheme

@Composable
@Preview
fun App() {
    iCaloryAITheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) {
            AppNavGraph(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            )
        }
    }
}