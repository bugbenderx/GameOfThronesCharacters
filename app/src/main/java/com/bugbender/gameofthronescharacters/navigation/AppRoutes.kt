package com.bugbender.gameofthronescharacters.navigation

import kotlinx.serialization.Serializable

interface Route

@Serializable
object CharacterRoute: Route

@Serializable
object FavoritesRoute: Route

@Serializable
object SettingsRoute: Route