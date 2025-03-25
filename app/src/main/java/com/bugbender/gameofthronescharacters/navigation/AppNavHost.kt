package com.bugbender.gameofthronescharacters.navigation

import android.net.Uri
import android.os.Bundle
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.bugbender.gameofthronescharacters.character.presentation.CharacterScreen
import com.bugbender.gameofthronescharacters.favorites.presentation.FavoriteCharacterDetailsScreen
import com.bugbender.gameofthronescharacters.favorites.presentation.FavoriteCharacterUi
import com.bugbender.gameofthronescharacters.favorites.presentation.FavoritesScreen
import com.bugbender.gameofthronescharacters.settings.presentation.SettingsScreen
import com.bugbender.gameofthronescharacters.settings.presentation.SettingsViewModel
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun AppNavHost(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Graph.Character,
    ) {
        navigation<Graph.Character>(startDestination = Route.Character) {

            composable<Route.Character> {
                CharacterScreen()
            }
        }

        navigation<Graph.Favorites>(startDestination = Route.FavoriteCharacters) {

            composable<Route.FavoriteCharacters>(
                enterTransition = {
                    val enterTransition = slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                    enterTransition
                }
            ) {
                FavoritesScreen(
                    navigateToCharacterScreen = {
                        navController.navigate(Graph.Character.startDestination) {
                            popUpTo(Route.FavoriteCharacters) { inclusive = true }
                            restoreState = true
                        }
                    },
                    navigateToFavoriteCharacterDetailsScreen = { favoriteCharacterUi ->
                        navController.navigate(Route.FavoriteCharacterDetails(favoriteCharacterUi)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<Route.FavoriteCharacterDetails>(
                typeMap = mapOf(typeOf<FavoriteCharacterUi>() to FavoriteCharacterUiNavType),
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }
            ) {
                val route = it.toRoute<Route.FavoriteCharacterDetails>()

                FavoriteCharacterDetailsScreen(
                    favoriteCharacterUi = route.character,
                    navigateBack = { navController.navigateUp() }
                )
            }
        }

        navigation<Graph.Settings>(startDestination = Route.Settings) {

            composable<Route.Settings>(
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(800)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            ) {
                SettingsScreen(settingsViewModel)
            }
        }
    }
}

val FavoriteCharacterUiNavType = object : NavType<FavoriteCharacterUi>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: FavoriteCharacterUi) =
        bundle.putString(key, Json.encodeToString(value))

    override fun get(bundle: Bundle, key: String): FavoriteCharacterUi? =
        bundle.getString(key)?.let { Json.decodeFromString(it) }

    override fun serializeAsValue(value: FavoriteCharacterUi): String =
        Uri.encode(Json.encodeToString(value))

    override fun parseValue(value: String): FavoriteCharacterUi =
        Json.decodeFromString(Uri.decode(value))
}