package com.bugbender.gameofthronescharacters.favorites.presentation

import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharacter

object FavoriteDomainToUiMapper : FavoriteCharacter.Mapper<FavoriteCharacterUi> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>
    ) = FavoriteCharacterUi(
        id = id,
        name = name,
        actor = actor,
        debut = debut,
        imageUrl = imageUrl,
        description = description,
        memorableMoments = memorableMoments
    )
}