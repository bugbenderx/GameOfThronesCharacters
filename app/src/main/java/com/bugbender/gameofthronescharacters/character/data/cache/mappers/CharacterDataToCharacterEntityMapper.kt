package com.bugbender.gameofthronescharacters.character.data.cache.mappers

import com.bugbender.gameofthronescharacters.character.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity

object CharacterDataToCharacterEntityMapper : CharacterData.Mapper<CharacterEntity> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>
    ) = CharacterEntity(
        id = id,
        name = name,
        actor = actor,
        debut = debut,
        imageUrl = imageUrl,
        description = description
    )
}