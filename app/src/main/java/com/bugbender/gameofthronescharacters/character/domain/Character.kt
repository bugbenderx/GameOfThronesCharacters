package com.bugbender.gameofthronescharacters.character.domain

data class Character(
    val id: Int,
    val name: String,
    val actor: String,
    val debut: String,
    val imageUrl: String,
    val description: String,
    val memorableMoments: List<String>,
    val isFavorite: Boolean
)