package com.bugbender.gameofthronescharacters.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bugbender.gameofthronescharacters.R

@Composable
fun AppNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            selected = currentDestination?.hasRoute<CharacterRoute>() ?: false,
            onClick = {
                navController.navigateSingleTopTo(CharacterRoute)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.character),
                    contentDescription = stringResource(R.string.character_navigation_icon)
                )
            },
            label = {
                Text(text = stringResource(R.string.character))
            }
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute<FavoritesRoute>() ?: false,
            onClick = {
                navController.navigateSingleTopTo(FavoritesRoute)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.favorites),
                    contentDescription = stringResource(R.string.favorites_navigation_icon)
                )
            },
            label = {
                Text(text = stringResource(R.string.favorites))
            }
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute<SettingsRoute>() ?: false,
            onClick = {
                navController.navigateSingleTopTo(SettingsRoute)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = stringResource(R.string.settings_navigation_icon)
                )
            },
            label = {
                Text(text = stringResource(R.string.settings))
            }
        )
    }
}