package com.bugbender.gameofthronescharacters.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.character.presentation.CharacterScreen
import com.bugbender.gameofthronescharacters.core.presentation.theme.fixedColorScheme
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
                    .paint(
                        painter = painterResource(R.drawable.the_wall),
                        contentScale = ContentScale.Crop
                    )

            ) {
                Text(
                    text = "Will be implemented soon",
                    color = MaterialTheme.fixedColorScheme.surface.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
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