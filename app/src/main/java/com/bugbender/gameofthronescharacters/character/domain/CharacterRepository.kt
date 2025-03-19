package com.bugbender.gameofthronescharacters.character.domain

interface CharacterRepository {

    suspend fun getRandom(): LoadResult

    suspend fun addToFavorites(character: Character)

    suspend fun deleteFromFavorites(characterId: Int)
}