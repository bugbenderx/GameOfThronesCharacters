package com.bugbender.gameofthronescharacters.character.data

import coil3.request.ErrorResult
import com.bugbender.gameofthronescharacters.character.data.cache.FavoriteCharacterCacheDataSource
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterCloudDataSource
import com.bugbender.gameofthronescharacters.character.data.mappers.CharacterDataToDomainMapper
import com.bugbender.gameofthronescharacters.character.data.mappers.CharacterDomainToDataMapper
import com.bugbender.gameofthronescharacters.character.domain.Character
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import com.bugbender.gameofthronescharacters.character.domain.LoadResult
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val cloudDataSource: CharacterCloudDataSource,
    private val cacheDataSource: FavoriteCharacterCacheDataSource,
    private val imageLoader: LoadImage,
    private val handleError: HandleError
) : CharacterRepository {

    override suspend fun getRandom(): LoadResult = try {
        val characterData = cloudDataSource.getRandom()

        val imageResult = imageLoader.download(characterData.imageUrl)
        if (imageResult is ErrorResult) throw imageResult.throwable

        val isFavorite = cacheDataSource.isExists(characterData.id)
        LoadResult.Success(characterData.map(CharacterDataToDomainMapper(isFavorite)))
    } catch (e: Exception) {
        val (message, advice) = handleError.handle(e)
        LoadResult.Error(message = message, advice = advice)
    }

    override suspend fun addToFavorites(character: Character) {
        cacheDataSource.save(character.map(CharacterDomainToDataMapper))
    }

    override suspend fun deleteFromFavorites(characterId: Int) {
        cacheDataSource.deleteById(characterId)
    }
}