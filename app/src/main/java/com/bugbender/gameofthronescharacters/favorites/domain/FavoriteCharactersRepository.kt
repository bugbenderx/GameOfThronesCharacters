package com.bugbender.gameofthronescharacters.favorites.domain

interface FavoriteCharactersRepository {

    suspend fun delete(characterId: Int)

    suspend fun getAll(): List<FavoriteCharacter>
}

