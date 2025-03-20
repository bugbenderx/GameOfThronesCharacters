package com.bugbender.gameofthronescharacters.favorites.domain

import kotlinx.coroutines.flow.Flow

interface FavoriteCharactersRepository {

    fun getAllFavorites(): Flow<List<FavoriteCharacter>>

    suspend fun delete(characterId: Int)
}