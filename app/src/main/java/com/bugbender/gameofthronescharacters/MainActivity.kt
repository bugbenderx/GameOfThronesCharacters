package com.bugbender.gameofthronescharacters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bugbender.gameofthronescharacters.navigation.AppNavHost
import com.bugbender.gameofthronescharacters.navigation.AppNavigationBar
import com.bugbender.gameofthronescharacters.core.theme.GameOfThronesCharactersTheme

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