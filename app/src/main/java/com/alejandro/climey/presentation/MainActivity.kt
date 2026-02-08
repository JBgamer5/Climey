package com.alejandro.climey.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.alejandro.climey.core.ui.theme.ClimeyTheme
import com.alejandro.climey.presentation.navigation.MainNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ClimeyTheme {
                Scaffold { innerPadding ->
                    MainNavGraph(innerPadding)
                }
            }
        }
    }
}