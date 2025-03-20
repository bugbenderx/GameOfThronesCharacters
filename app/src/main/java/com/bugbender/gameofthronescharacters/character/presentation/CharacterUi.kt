package com.bugbender.gameofthronescharacters.character.presentation

import com.bugbender.gameofthronescharacters.character.domain.Character

data class CharacterUi(
    val id: Int,
    val name: String,
    val actor: String,
    val debut: String,
    val imageUrl: String,
    val description: String,
    val memorableMoments: List<String>,
    val isFavorite: Boolean
) {
    fun toDomain() = Character(
        id, name, actor, debut, imageUrl, description, memorableMoments, isFavorite
    )
}