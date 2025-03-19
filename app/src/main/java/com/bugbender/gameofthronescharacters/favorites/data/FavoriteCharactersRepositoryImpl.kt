package com.bugbender.gameofthronescharacters.favorites.data

import com.bugbender.gameofthronescharacters.favorites.data.cache.FavoriteCharactersCacheDataSource
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharacter
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharactersRepository
import javax.inject.Inject

class FavoriteCharactersRepositoryImpl @Inject constructor(
    private val cacheDataSource: FavoriteCharactersCacheDataSource
) : FavoriteCharactersRepository {

    override suspend fun delete(characterId: Int) = cacheDataSource.deleteById(characterId)

    override suspend fun getAll(): List<FavoriteCharacter> = cacheDataSource.getAll()
        .map { it.map(CharacterDataToDomainFavoriteMapper) }
}

