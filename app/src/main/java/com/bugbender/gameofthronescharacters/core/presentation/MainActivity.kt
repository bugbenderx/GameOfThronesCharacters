package com.bugbender.gameofthronescharacters.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import com.bugbender.gameofthronescharacters.navigation.AppNavHost
import com.bugbender.gameofthronescharacters.navigation.AppNavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameOfThronesCharactersTheme {
                GameOfThronesCharactersApp()
            }
        }
    }
}

@Composable
fun GameOfThronesCharactersApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppNavigationBar(navController)
        }
    ) {  innerPadding ->
        AppNavHost(
            navController = navController
        )
    }
}