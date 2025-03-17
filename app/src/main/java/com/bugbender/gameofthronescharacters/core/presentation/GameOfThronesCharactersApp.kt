package com.bugbender.gameofthronescharacters.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bugbender.gameofthronescharacters.navigation.AppNavHost
import com.bugbender.gameofthronescharacters.navigation.AppNavigationBar
import com.bugbender.gameofthronescharacters.settings.presentation.SettingsViewModel

@Composable
fun GameOfThronesCharactersApp(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppNavigationBar(navController)
        },
    ) { innerPadding ->
        AppNavHost(
            settingsViewModel = settingsViewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        )
    }
}