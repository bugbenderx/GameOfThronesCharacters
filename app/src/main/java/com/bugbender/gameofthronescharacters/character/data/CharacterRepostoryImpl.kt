package com.bugbender.gameofthronescharacters.character.data

import com.bugbender.gameofthronescharacters.character.data.cache.FavoriteCharacterCacheDataSource
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterCloudDataSource
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import com.bugbender.gameofthronescharacters.character.domain.LoadResult
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val cloudDataSource: CharacterCloudDataSource,
    private val cacheDataSource: FavoriteCharacterCacheDataSource,
    private val handleError: HandleError
) : CharacterRepository {

    override suspend fun getRandom(): LoadResult =
        try {
            val characterData = cloudDataSource.getRandom()
            val isFavorite = cacheDataSource.isExists(characterData.id)
            LoadResult.Success(characterData.map(CharacterDataToDomainMapper(isFavorite)))
        } catch (e: Exception) {
            LoadResult.Error(message = handleError.handle(e))
        }
}