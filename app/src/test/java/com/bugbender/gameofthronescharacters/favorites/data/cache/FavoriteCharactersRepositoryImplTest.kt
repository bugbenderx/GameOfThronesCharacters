package com.bugbender.gameofthronescharacters.favorites.data.cache

import com.bugbender.gameofthronescharacters.character.data.cache.FakeCharacterDao
import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FavoriteCharactersRepositoryImplTest {
    private lateinit var fakeCharacterDao: FakeCharacterDao
    private lateinit var cacheDataSource: FavoriteCharactersCacheDataSource

    @Before
    fun setUp() {
        fakeCharacterDao = FakeCharacterDao()
        cacheDataSource = FavoriteCharactersCacheDataSource.Impl(characterDao = fakeCharacterDao)

        fakeCharacterDao.characters.add(
            CharacterEntity(
                id = 22,
                name = "Jon Snow",
                actor = "Kit Harington",
                debut = "Season 1",
                imageUrl = "url1",
                description = "Description1",
            )
        )

        fakeCharacterDao.memorableMoments.addAll(
            listOf(
                MemorableMomentEntity(id = 1, characterId = 22, value = "First memorable moment"),
                MemorableMomentEntity(id = 2, characterId = 22, value = "Second memorable moment")
            )
        )
    }

    @Test
    fun mainScenario() = runBlocking {
        var actualFavoriteCharacters = cacheDataSource.getCharacters()

        val expectedFavoriteCharacters = listOf(
            CharacterData(
                id = 22,
                name = "Jon Snow",
                actor = "Kit Harington",
                debut = "Season 1",
                imageUrl = "url1",
                description = "Description1",
                memorableMoments = listOf("First memorable moment", "Second memorable moment")
            )
        )
        assertEquals(expectedFavoriteCharacters, actualFavoriteCharacters)

        cacheDataSource.deleteById(id = 22)

        actualFavoriteCharacters = cacheDataSource.getCharacters()
        assertTrue(actualFavoriteCharacters.isEmpty())
    }
}