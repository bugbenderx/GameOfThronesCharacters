package com.bugbender.gameofthronescharacters.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.favorites.presentation.FavoriteCharacterUi
import kotlinx.serialization.Serializable

interface Route {

    @Serializable
    data object Character : Route

    @Serializable
    data object FavoriteCharacters : Route

    @Serializable
    data class FavoriteCharacterDetails(val character: FavoriteCharacterUi) : Route

    @Serializable
    data object Settings : Route
}

@Serializable
sealed class Graph(
    val startDestination: Route,
    @DrawableRes val iconResId: Int,
    @StringRes val contentDescriptionResId: Int,
    @StringRes val labelResId: Int
) {
    @Serializable
    data object Character : Graph(
        startDestination = Route.Character,
        iconResId = R.drawable.character,
        contentDescriptionResId = R.string.character_navigation_icon,
        labelResId = R.string.character
    )

    @Serializable
    data object Favorites : Graph(
        startDestination = Route.FavoriteCharacters,
        iconResId = R.drawable.favorites,
        contentDescriptionResId = R.string.favorites_navigation_icon,
        labelResId = R.string.favorites
    )

    @Serializable
    data object Settings : Graph(
        startDestination = Route.Settings,
        iconResId = R.drawable.settings,
        contentDescriptionResId = R.string.settings_navigation_icon,
        labelResId = R.string.settings
    )
}