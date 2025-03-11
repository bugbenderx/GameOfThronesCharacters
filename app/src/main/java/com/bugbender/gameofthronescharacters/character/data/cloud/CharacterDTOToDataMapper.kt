package com.bugbender.gameofthronescharacters.character.data.cloud

import com.bugbender.gameofthronescharacters.character.data.CharacterData

object CharacterDTOToDataMapper : CharacterDTO.Mapper<CharacterData> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>
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