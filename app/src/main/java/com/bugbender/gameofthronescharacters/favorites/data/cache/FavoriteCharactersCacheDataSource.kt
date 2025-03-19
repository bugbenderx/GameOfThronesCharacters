package com.bugbender.gameofthronescharacters.favorites.data.cache

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterDao
import javax.inject.Inject

interface FavoriteCharactersCacheDataSource {

    suspend fun deleteById(id: Int)

    suspend fun getAll(): List<CharacterData>

    class Impl @Inject constructor(
        private val characterDao: CharacterDao
    ) : FavoriteCharactersCacheDataSource {

        override suspend fun deleteById(id: Int) = characterDao.deleteById(id)

        override suspend fun getAll(): List<CharacterData> = characterDao.getAll()
            .map { it.map(CharacterWithMemorableMomentsToData) }
    }
}