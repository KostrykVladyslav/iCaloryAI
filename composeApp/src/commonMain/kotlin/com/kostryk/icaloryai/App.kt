package com.kostryk.icaloryai

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.graph.AppNavGraph
import com.kostryk.icaloryai.theme.iCaloryAITheme
import org.koin.compose.koinInject

@Composable
fun App() {
    val themeManager = koinInject<ThemeManager>()
    val themeType by themeManager.observeThemeChange().collectAsState()

    iCaloryAITheme(themeType = themeType) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            AppNavGraph(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}