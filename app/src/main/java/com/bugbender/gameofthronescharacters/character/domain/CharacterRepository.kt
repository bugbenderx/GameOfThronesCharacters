package com.bugbender.gameofthronescharacters.character.domain

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getFavorite(id: Int): Flow<Character?>

    suspend fun getRandom(): LoadResult

    suspend fun addToFavorites(character: Character)

    suspend fun deleteFromFavorites(characterId: Int)
}