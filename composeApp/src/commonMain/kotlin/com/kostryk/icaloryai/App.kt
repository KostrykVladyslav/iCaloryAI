package com.kostryk.icaloryai

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.arch.manager.theme.ThemeManagerImpl
import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.graph.AppNavGraph
import com.kostryk.icaloryai.theme.iCaloryAITheme
import com.kostryk.icaloryai.theme.isDarkTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        val themeManager = koinInject<ThemeManager>()
        val theme: State<ThemeType> = themeManager.observeThemeChange().collectAsState()
        val themeType = theme.value
        iCaloryAITheme(
            isDarkTheme = themeType == ThemeType.DARK || themeType == ThemeType.SYSTEM && isDarkTheme()
        ) {
            val navController = rememberNavController()
            Scaffold(modifier = Modifier.fillMaxSize()) {
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        }
    }
}