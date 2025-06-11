package com.kostryk.icaloryai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.kostryk.icaloryai.ui.main.elements.DishLoadingSection
import com.kostryk.icaloryai.ui.main.elements.DishSection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        enableEdgeToEdge()
        setContent { App() }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    Column {
        DishSection(
            title = "Chicken Salad",
            calories = 350,
            protein = 30,
            fat = 15,
            carbs = 20,
            time = "14:32",
            modifier = Modifier
        )
        Spacer(Modifier.height(12.dp))
        DishLoadingSection()


    }
}