package com.bugbender.gameofthronescharacters.core.data.cache.models

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithMemorableMoments(
    @Embedded
    val characterEntity: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "character_id"
    )
    val memorableMoments: List<MemorableMomentEntity>
) {
    interface Mapper<T> {
        fun map(characterEntity: CharacterEntity, memorableMoments: List<MemorableMomentEntity>): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(characterEntity, memorableMoments)
}