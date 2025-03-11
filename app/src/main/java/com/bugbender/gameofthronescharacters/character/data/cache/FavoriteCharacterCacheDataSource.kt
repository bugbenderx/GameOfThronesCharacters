package com.bugbender.gameofthronescharacters.character.data.cache

import com.bugbender.gameofthronescharacters.character.data.CharacterData
import com.bugbender.gameofthronescharacters.character.data.cache.mappers.CharacterDataToCharacterEntityMapper
import com.bugbender.gameofthronescharacters.character.data.cache.mappers.CharacterDataToMemorableMomentEntitiesMapper
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterDao
import javax.inject.Inject

interface FavoriteCharacterCacheDataSource {

    suspend fun save(character: CharacterData)

    suspend fun deleteById(id: Int)

    suspend fun isExists(id: Int): Boolean

    class Impl @Inject constructor(
        private val characterDao: CharacterDao
    ) : FavoriteCharacterCacheDataSource {

        override suspend fun save(character: CharacterData) {
            characterDao.insertCharacter(character.map(CharacterDataToCharacterEntityMapper))
            characterDao.insertMemorableMoments(
                character.map(CharacterDataToMemorableMomentEntitiesMapper)
            )
        }

        override suspend fun deleteById(id: Int) = characterDao.deleteById(id)

        override suspend fun isExists(id: Int): Boolean = characterDao.isExists(id)
    }
}

