package com.bugbender.gameofthronescharacters.character.data.cache

import com.bugbender.gameofthronescharacters.character.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.CharacterDao
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterWithMemorableMoments
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FavoriteCharacterCacheDataSourceImplTest {

    private lateinit var fakeCharacterDao: FakeCharacterDao
    private lateinit var cacheDataSource: FavoriteCharacterCacheDataSource

    @Before
    fun setUp() {
        fakeCharacterDao = FakeCharacterDao()
        cacheDataSource = FavoriteCharacterCacheDataSource.Impl(fakeCharacterDao)
    }

    @Test
    fun mainScenario() = runBlocking {
        val characterData = CharacterData(
            id = 22,
            name = "Jon Snow",
            actor = "Kit Harington",
            debut = "Season 1",
            imageUrl = "url1",
            description = "Description1",
            memorableMoments = listOf("First memorable moment", "Second memorable moment")
        )

        cacheDataSource.save(characterData)
        assertEquals(1, fakeCharacterDao.characters.size)
        assertEquals(2, fakeCharacterDao.memorableMoments.size)

        var isExists = cacheDataSource.isExists(id = 22)
        assertTrue(isExists)

        cacheDataSource.deleteById(22)
        assertEquals(0, fakeCharacterDao.characters.size)
        assertEquals(0, fakeCharacterDao.memorableMoments.size)

        isExists = cacheDataSource.isExists(id = 22)
        assertFalse(isExists)
    }
}

class FakeCharacterDao : CharacterDao {
    val characters = mutableListOf<CharacterEntity>()
    val memorableMoments = mutableListOf<MemorableMomentEntity>()

    override suspend fun insertCharacter(entity: CharacterEntity) {
        characters.add(entity)
    }

    override suspend fun insertMemorableMoments(entities: List<MemorableMomentEntity>) {
        memorableMoments.addAll(entities)
    }

    override suspend fun deleteById(id: Int) {
        characters.removeAll { it.id == id }
        memorableMoments.removeAll { it.characterId == id }
    }

    override suspend fun isExists(id: Int): Boolean {
        return characters.any { it.id == id }
    }

    override suspend fun getAll(): List<CharacterWithMemorableMoments> {
        return characters.map { character ->
            val moments = memorableMoments.filter { it.characterId == character.id }
            CharacterWithMemorableMoments(characterEntity = character, memorableMoments = moments)
        }
    }
}