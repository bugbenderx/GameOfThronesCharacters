package com.bugbender.gameofthronescharacters.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bugbender.gameofthronescharacters.navigation.AppNavHost
import com.bugbender.gameofthronescharacters.navigation.AppNavigationBar

@Composable
fun GameOfThronesCharactersApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppNavigationBar(navController)
        },
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        )
    }
}