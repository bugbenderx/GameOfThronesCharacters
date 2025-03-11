package com.bugbender.gameofthronescharacters.character.data

import com.bugbender.gameofthronescharacters.character.domain.Character

class CharacterDataToDomainMapper(
    private val isFavorite: Boolean
) : CharacterData.Mapper<Character> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>
    ) = Character(
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