package com.bugbender.gameofthronescharacters.favorites.data.cache

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterDao
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterWithMemorableMomentsToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavoriteCharactersCacheDataSource {

    fun getCharacters(): Flow<List<CharacterData>>

    suspend fun deleteById(id: Int)

    class Impl @Inject constructor(
        private val characterDao: CharacterDao
    ) : FavoriteCharactersCacheDataSource {

        override fun getCharacters(): Flow<List<CharacterData>> = characterDao.getAll()
            .map { list -> list.map { it.map(CharacterWithMemorableMomentsToData) } }

        override suspend fun deleteById(id: Int) = characterDao.deleteById(id)
    }
}