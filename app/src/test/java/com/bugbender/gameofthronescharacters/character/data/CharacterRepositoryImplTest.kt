package com.bugbender.gameofthronescharacters.character.data

import com.bugbender.gameofthronescharacters.character.data.cache.FavoriteCharacterCacheDataSource
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterCloudDataSource
import com.bugbender.gameofthronescharacters.character.domain.Character
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import com.bugbender.gameofthronescharacters.character.domain.LoadResult
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImplTest {

    private lateinit var repository: CharacterRepository
    private lateinit var cloudDataSource: FakeCharacterCloudDataSource
    private lateinit var cacheDataSource: FavoriteCharacterCacheDataSource
    private lateinit var handleError: HandleError

    @Before
    fun setUp() {
        cloudDataSource = FakeCharacterCloudDataSource()
        cacheDataSource = FakeFavoriteCharacterCacheDataSource()
        handleError = FakeHandleError()

        repository = CharacterRepositoryImpl(cloudDataSource, cacheDataSource, handleError)
    }

    @Test
    fun `getRandom should return success with character data`() = runBlocking {
        val result = repository.getRandom()

        assertTrue(result is LoadResult.Success)
        val successResult = result as LoadResult.Success
        val expectedCharacter = Character(
            id = 22,
            name = "Jon Snow",
            actor = "Kit Harington",
            debut = "Season 1",
            imageUrl = "url1",
            description = "Description1",
            memorableMoments = listOf("First memorable moment", "Second memorable moment"),
            isFavorite = true
        )
        assertEquals(expectedCharacter, successResult.character)
    }

    @Test
    fun `getRandom should return error when exception occurs`() = runBlocking {
        cloudDataSource.returnSuccess = false

        val result = repository.getRandom()

        assertTrue(result is LoadResult.Error)
        val errorResult = result as LoadResult.Error
        assertEquals("serviceUnavailableTryLater", errorResult.message)
    }
}

class FakeCharacterCloudDataSource : CharacterCloudDataSource {

    var returnSuccess = true
    override suspend fun getRandom(): CharacterData =
        if (returnSuccess)
            CharacterData(
                id = 22,
                name = "Jon Snow",
                actor = "Kit Harington",
                debut = "Season 1",
                imageUrl = "url1",
                description = "Description1",
                memorableMoments = listOf(
                    "First memorable moment",
                    "Second memorable moment"
                )
            )
        else throw Exception()
}

class FakeFavoriteCharacterCacheDataSource : FavoriteCharacterCacheDataSource {
    override suspend fun save(character: CharacterData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun isExists(id: Int) = true // Pretend the character is a favorite
}

class FakeHandleError : HandleError {
    override fun handle(e: Exception): String {
        return "serviceUnavailableTryLater"
    }
}