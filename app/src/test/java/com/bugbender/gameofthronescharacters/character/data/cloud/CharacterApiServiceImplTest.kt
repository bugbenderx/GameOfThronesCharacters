package com.bugbender.gameofthronescharacters.character.data.cloud

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterApiServiceTest {

    @Test
    fun `getRandom should return CharacterDTO when the API call is successful`() = runBlocking {
        val mockEngine = MockEngine { request ->
            assertEquals("/characters/random", request.url.encodedPath)
            respond(
                content = ByteReadChannel(
                    """{
                        "id": 1,
                        "name": "Jon Snow",
                        "actor": "Kit Harington",
                        "debut": "Season 1",
                        "imageUrl": "url1",
                        "description": "Description1",
                        "memorableMoments": [
                            "First memorable moment",
                            "Second memorable moment"
                        ]
                    }"""
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }
        val apiService = CharacterApiService.Impl(client)

        val resultDTO = apiService.getRandom()
        val expectedDTO = CharacterDto(
            id = 1,
            name = "Jon Snow",
            actor = "Kit Harington",
            debut = "Season 1",
            imageUrl = "url1",
            description = "Description1",
            memorableMoments = listOf("First memorable moment", "Second memorable moment")
        )

        assertEquals(expectedDTO, resultDTO)
    }

    @Test(expected = Exception::class)
    fun `getRandom should throw an exception when the API call fails`(): Unit = runBlocking {
        val mockEngine = MockEngine { request ->
            respondError(HttpStatusCode.InternalServerError)
        }

        val client = HttpClient(mockEngine)
        val apiService = CharacterApiService.Impl(client)

        apiService.getRandom()
    }
}