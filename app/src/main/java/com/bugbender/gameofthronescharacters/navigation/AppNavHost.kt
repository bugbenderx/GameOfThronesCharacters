package com.bugbender.gameofthronescharacters.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bugbender.gameofthronescharacters.character.presentation.CharacterScreen
import com.bugbender.gameofthronescharacters.settings.presentation.SettingsScreen
import com.bugbender.gameofthronescharacters.settings.presentation.SettingsViewModel

@Composable
fun AppNavHost(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = CharacterRoute,
        modifier = modifier
    ) {
        composable<CharacterRoute> {
            CharacterScreen()
        }
        composable<FavoritesRoute> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)

            ) {
                Text(text = "FavoritesScreen")
            }
        }
        composable<SettingsRoute> {
            SettingsScreen(settingsViewModel)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: Route) = currentDestination?.let { destination ->
    if (!destination.hasRoute(route::class)) {
        popBackStack()
        navigate(route)
    }
}