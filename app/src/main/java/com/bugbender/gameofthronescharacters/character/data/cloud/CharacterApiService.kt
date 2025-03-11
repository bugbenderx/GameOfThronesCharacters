package com.bugbender.gameofthronescharacters.character.data.cloud

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface CharacterApiService {

    suspend fun getRandom(): CharacterDTO

    class Impl @Inject constructor(private val client: HttpClient) : CharacterApiService {

        override suspend fun getRandom(): CharacterDTO =
            client
                .get("/characters/random")
                .body<CharacterDTO>()
    }
}