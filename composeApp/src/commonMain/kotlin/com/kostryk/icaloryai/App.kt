package com.kostryk.icaloryai

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.graph.AppNavGraph
import com.kostryk.icaloryai.theme.iCaloryAITheme
import org.koin.compose.koinInject

@Composable
fun App() {
    val themeManager = koinInject<ThemeManager>()
    val themeType by themeManager.observeThemeChange().collectAsState()

    iCaloryAITheme(themeType = themeType) {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) {
            AppNavGraph(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}