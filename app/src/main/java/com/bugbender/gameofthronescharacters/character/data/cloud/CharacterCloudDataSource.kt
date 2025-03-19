package com.bugbender.gameofthronescharacters.character.data.cloud

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import javax.inject.Inject


interface CharacterCloudDataSource {

    suspend fun getRandom(): CharacterData

    class Impl @Inject constructor(
        private val apiService: CharacterApiService
    ) : CharacterCloudDataSource {

        override suspend fun getRandom(): CharacterData =
            apiService.getRandom().map(CharacterDtoToDataMapper)
    }
}