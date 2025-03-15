package com.bugbender.gameofthronescharacters.character.domain

sealed interface LoadResult {
    data class Success(val character: Character) : LoadResult
    data class Error(val message: String, val advice: String) : LoadResult
}