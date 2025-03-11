package com.bugbender.gameofthronescharacters.character.domain

interface CharacterRepository {

    suspend fun getRandom(): LoadResult
}