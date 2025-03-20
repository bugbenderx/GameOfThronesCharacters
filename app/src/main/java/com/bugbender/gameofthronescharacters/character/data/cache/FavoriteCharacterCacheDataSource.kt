package com.bugbender.gameofthronescharacters.character.data.cache

import com.bugbender.gameofthronescharacters.character.data.cache.mappers.CharacterDataToCharacterEntityMapper
import com.bugbender.gameofthronescharacters.character.data.cache.mappers.CharacterDataToMemorableMomentEntitiesMapper
import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterDao
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterWithMemorableMomentsToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavoriteCharacterCacheDataSource {

    fun getById(id: Int): Flow<CharacterData?>

    suspend fun save(character: CharacterData)

    suspend fun deleteById(id: Int)

    suspend fun isExists(id: Int): Boolean

    class Impl @Inject constructor(
        private val characterDao: CharacterDao
    ) : FavoriteCharacterCacheDataSource {

        override fun getById(id: Int): Flow<CharacterData?> =
            characterDao.getById(id).map { it?.map(CharacterWithMemorableMomentsToData) }

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

