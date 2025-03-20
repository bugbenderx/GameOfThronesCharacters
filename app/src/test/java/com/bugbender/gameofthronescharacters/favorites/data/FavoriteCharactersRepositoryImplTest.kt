package com.bugbender.gameofthronescharacters.favorites.data

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.favorites.data.cache.FavoriteCharactersCacheDataSource
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharacter
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FavoriteCharactersRepositoryImplTest {

    private lateinit var repository: FavoriteCharactersRepository

    @Before
    fun setUp() {
        val fakeCacheDataSource = FakeFavoriteCharactersCacheDataSource()
        val character = CharacterData(
            id = 1,
            name = "Arya Stark",
            actor = "Maisie Williams",
            debut = "Season 1",
            imageUrl = "https://example.com/arya.jpg",
            description = "A faceless assassin",
            memorableMoments = listOf("Defeating the Night King")
        )
        fakeCacheDataSource.characters.add(character)

        repository = FavoriteCharactersRepositoryImpl(cacheDataSource = fakeCacheDataSource)
    }

    @Test
    fun mainScenario() = runBlocking {
        var actualFavoriteCharacters = repository.getAllFavorites()
        val expectedFavoriteCharacters = listOf(
            FavoriteCharacter(
                id = 1,
                name = "Arya Stark",
                actor = "Maisie Williams",
                debut = "Season 1",
                imageUrl = "https://example.com/arya.jpg",
                description = "A faceless assassin",
                memorableMoments = listOf("Defeating the Night King")
            )
        )

        assertEquals(actualFavoriteCharacters, expectedFavoriteCharacters)

        repository.delete(characterId = 1)

        actualFavoriteCharacters = repository.getAllFavorites()
        assertTrue(actualFavoriteCharacters.isEmpty())
    }
}


class FakeFavoriteCharactersCacheDataSource : FavoriteCharactersCacheDataSource {

    val characters = mutableListOf<CharacterData>()

    override fun getCharacters(): Flow<List<CharacterData>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Int) {
        characters.removeAll { it.id == id }
    }

    override suspend fun getAll(): List<CharacterData> {
        return characters.toList()
    }
}
