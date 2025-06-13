package com.kostryk.icaloryai

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.coroutineScope
import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val themeManager by inject<ThemeManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        setContent { App() }
        observeTheme()
    }

    private fun observeTheme() {
        lifecycle.coroutineScope.launch {
            themeManager.observeThemeChange().collect {
                withContext(Dispatchers.Main) {
                    resources.configuration.uiMode = if (it == ThemeType.DARK) {
                        Configuration.UI_MODE_NIGHT_YES
                    } else {
                        Configuration.UI_MODE_NIGHT_NO
                    }
                }
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}