package com.bugbender.gameofthronescharacters.character.data.cache.mappers

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity

object CharacterDataToMemorableMomentEntitiesMapper :
    CharacterData.Mapper<List<MemorableMomentEntity>> {
    override fun map(
        id: Int,
        name: String,
        actor: String,
        debut: String,
        imageUrl: String,
        description: String,
        memorableMoments: List<String>
    ) = memorableMoments.map {
        MemorableMomentEntity(characterId = id, value = it)
    }
}