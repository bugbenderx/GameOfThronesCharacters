package com.bugbender.gameofthronescharacters.character.data.mappers

import com.bugbender.gameofthronescharacters.character.domain.Character
import com.bugbender.gameofthronescharacters.core.data.CharacterData

object CharacterDomainToDataMapper : Character.Mapper<CharacterData> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>,
        isFavorite: Boolean
    ) = CharacterData(
        id = id,
        name = name,
        actor = actor,
        debut = debut,
        imageUrl = imageUrl,
        description = description,
        memorableMoments = memorableMoments
    )
}