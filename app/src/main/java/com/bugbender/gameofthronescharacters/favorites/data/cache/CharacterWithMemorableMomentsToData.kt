package com.bugbender.gameofthronescharacters.favorites.data.cache

import com.bugbender.gameofthronescharacters.core.data.CharacterData
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterWithMemorableMoments
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity

object CharacterWithMemorableMomentsToData : CharacterWithMemorableMoments.Mapper<CharacterData> {
    override fun map(
        characterEntity: CharacterEntity,
        memorableMoments: List<MemorableMomentEntity>
    ) = CharacterData(
        id = characterEntity.id,
        name = characterEntity.name,
        actor = characterEntity.actor,
        debut = characterEntity.debut,
        imageUrl = characterEntity.imageUrl,
        description = characterEntity.description,
        memorableMoments = memorableMoments.map { it.value }
    )
}