package com.bugbender.gameofthronescharacters.favorites.data

import com.bugbender.gameofthronescharacters.favorites.data.cache.FavoriteCharactersCacheDataSource
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharacter
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteCharactersRepositoryImpl @Inject constructor(
    private val cacheDataSource: FavoriteCharactersCacheDataSource
) : FavoriteCharactersRepository {

    override fun getAllFavorites(): Flow<List<FavoriteCharacter>> =
        cacheDataSource.getCharacters().map { list ->
            list.map { characterData ->
                characterData.map(CharacterDataToDomainFavoriteMapper)
            }
        }

    override suspend fun delete(characterId: Int) = cacheDataSource.deleteById(characterId)
}