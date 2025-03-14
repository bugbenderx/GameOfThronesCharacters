package com.bugbender.gameofthronescharacters.character.presentation

import com.bugbender.gameofthronescharacters.character.domain.Character

object CharacterToUiMapper : Character.Mapper<CharacterUi> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>,
        isFavorite: Boolean
    ) = CharacterUi(
        id = id,
        name = name,
        actor = actor,
        debut = debut,
        imageUrl = imageUrl,
        description = description,
        memorableMoments = memorableMoments,
        isFavorite = isFavorite
    )
}