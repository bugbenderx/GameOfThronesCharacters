package com.bugbender.gameofthronescharacters.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bugbender.gameofthronescharacters.core.presentation.theme.LocalWindowType
import com.bugbender.gameofthronescharacters.core.presentation.theme.WindowType
import com.bugbender.gameofthronescharacters.navigation.AppNavHost
import com.bugbender.gameofthronescharacters.navigation.Graph
import com.bugbender.gameofthronescharacters.settings.presentation.SettingsViewModel

@Composable
fun GameOfThronesCharactersApp(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()

    val layoutType = if (LocalConfiguration.current.screenWidthDp < 600)
        NavigationSuiteType.NavigationBar
    else
        NavigationSuiteType.NavigationRail

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val navDestinations = listOf(Graph.Character, Graph.Favorites, Graph.Settings)
    val currentGraph = navDestinations.find { graph ->
        currentDestination?.hierarchy?.any { it.hasRoute(graph::class) } == true
    }

    val unselectedIconColor = MaterialTheme.colorScheme.outline
    val unselectedTextColor = MaterialTheme.colorScheme.outline
    val navSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            unselectedIconColor = unselectedIconColor,
            unselectedTextColor = unselectedTextColor,
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            unselectedIconColor = unselectedIconColor,
            unselectedTextColor = unselectedTextColor
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors()
    )

    val modifier = if (LocalWindowType.current == WindowType.Expanded) {
        Modifier.windowInsetsPadding(WindowInsets.displayCutout)
    } else Modifier

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        NavigationSuiteScaffold(
            navigationSuiteItems = {

                navDestinations.forEach { graph ->
                    item(
                        selected = currentGraph == graph,
                        onClick = {
                            navController.navigate(graph) {
                                popUpTo(currentGraph?.startDestination ?: return@navigate) {
                                    inclusive = true
                                    saveState = true
                                }
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(graph.iconResId),
                                contentDescription = stringResource(graph.contentDescriptionResId)
                            )
                        },
                        label = {
                            Text(text = stringResource(graph.labelResId))
                        },
                        colors = navSuiteItemColors
                    )
                }
            },
            layoutType = layoutType,
            modifier = modifier
        ) {
            AppNavHost(
                settingsViewModel = settingsViewModel,
                navController = navController,
            )
        }
    }
}