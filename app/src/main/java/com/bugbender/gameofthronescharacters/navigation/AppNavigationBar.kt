package com.bugbender.gameofthronescharacters.navigation

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val navBarItemColors = NavigationBarItemDefaults.colors(
        unselectedIconColor = MaterialTheme.colorScheme.outline,
        unselectedTextColor = MaterialTheme.colorScheme.outline,
    )

    val bottomNavDestinations = listOf(Graph.Character, Graph.Favorites, Graph.Settings)

    val currentGraph = bottomNavDestinations.find { graph ->
        currentDestination?.hierarchy?.any { it.hasRoute(graph::class) } == true
    }

    //LogCurrentDestination(navController)

    NavigationBar {
        bottomNavDestinations.forEach { graph ->
            NavigationBarItem(
                selected = currentGraph == graph,
                onClick = {
                    navController.navigate(graph) {
                        popUpTo(currentGraph!!.startDestination) {
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
                colors = navBarItemColors
            )
        }
    }
}

@Composable
fun LogCurrentDestination(navController: NavHostController, tag: String = "k0") {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    LaunchedEffect(currentDestination) {
        currentDestination?.let { destination ->
            val route = destination.route
            val id = destination.id
            val hierarchyRoutes = destination.hierarchy.map { it.route }.joinToString(" -> ")
            Log.d(
                tag,
                "Current destination: $destination \n\tid=$id,\n\troute=$route,\n\thierarchy=[$hierarchyRoutes]"
            )
        }
    }
}