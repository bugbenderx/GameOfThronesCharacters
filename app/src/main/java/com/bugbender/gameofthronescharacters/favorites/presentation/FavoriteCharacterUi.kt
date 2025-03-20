package com.bugbender.gameofthronescharacters.favorites.presentation

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteCharacterUi(
    val id: Int,
    val name: String,
    val actor: String,
    val debut: String,
    val imageUrl: String,
    val description: String,
    val memorableMoments: List<String>
)