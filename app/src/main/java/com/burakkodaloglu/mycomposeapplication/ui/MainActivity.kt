package com.burakkodaloglu.mycomposeapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.burakkodaloglu.mycomposeapplication.ui.main.ClothingTrackerApp
import com.burakkodaloglu.mycomposeapplication.ui.theme.MyComposeApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeApplicationTheme {
                ClothingTrackerApp()
            }
        }
    }
}