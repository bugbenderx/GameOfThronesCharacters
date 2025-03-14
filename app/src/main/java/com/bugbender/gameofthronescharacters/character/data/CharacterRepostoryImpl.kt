package com.bugbender.gameofthronescharacters.character.data

import coil3.request.ErrorResult
import com.bugbender.gameofthronescharacters.character.data.cache.FavoriteCharacterCacheDataSource
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterCloudDataSource
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import com.bugbender.gameofthronescharacters.character.domain.LoadResult
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val cloudDataSource: CharacterCloudDataSource,
    private val cacheDataSource: FavoriteCharacterCacheDataSource,
    private val imageLoader: LoadImage,
    private val handleError: HandleError
) : CharacterRepository {

    override suspend fun getRandom(): LoadResult =
        try {
            val characterData = cloudDataSource.getRandom()

            val imageResult = imageLoader.download(characterData.imageUrl)
            if (imageResult is ErrorResult) throw imageResult.throwable

            val isFavorite = cacheDataSource.isExists(characterData.id)
            LoadResult.Success(characterData.map(CharacterDataToDomainMapper(isFavorite)))
        } catch (e: Exception) {
            LoadResult.Error(message = handleError.handle(e))
        }
}

