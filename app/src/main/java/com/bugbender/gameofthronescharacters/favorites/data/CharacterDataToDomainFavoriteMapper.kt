package com.bugbender.gameofthronescharacters.favorites.data

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharacter

object CharacterDataToDomainFavoriteMapper : CharacterData.Mapper<FavoriteCharacter> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>
    ) = FavoriteCharacter(
        id = id,
        name = name,
        actor = actor,
        debut = debut,
        imageUrl = imageUrl,
        description = description,
        memorableMoments = memorableMoments
    )
}