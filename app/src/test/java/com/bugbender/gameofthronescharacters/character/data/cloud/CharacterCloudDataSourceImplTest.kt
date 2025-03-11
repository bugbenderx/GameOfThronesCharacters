package com.bugbender.gameofthronescharacters.character.data.cloud

import com.bugbender.gameofthronescharacters.character.data.CharacterData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterCloudDataSourceImplTest {

    @Test
    fun mainScenario() = runBlocking {
        val fakeApiService = FakeCharacterApiService()
        val cloudDataSource = CharacterCloudDataSource.Impl(fakeApiService)

        val actualCharacter = cloudDataSource.getRandom()
        val expectedCharacter = CharacterData(
            id = 22,
            name = "Jon Snow",
            actor = "Kit Harington",
            debut = "Season 1",
            imageUrl = "url1",
            description = "Description1",
            memorableMoments = listOf("First memorable moment", "Second memorable moment")
        )
        assertEquals(expectedCharacter, actualCharacter)
    }
}

class FakeCharacterApiService : CharacterApiService {

    override suspend fun getRandom() = CharacterDTO(
        id = 22,
        name = "Jon Snow",
        actor = "Kit Harington",
        debut = "Season 1",
        imageUrl = "url1",
        description = "Description1",
        memorableMoments = listOf("First memorable moment", "Second memorable moment")
    )
}