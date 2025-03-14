package com.bugbender.gameofthronescharacters.character.data.cloud

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val actor: String,
    val debut: String,
    val imageUrl: String,
    val description: String,
    val memorableMoments: List<String>
) {
    interface Mapper<T> {
        fun map(
            id: Int,
            name: String,
            actor: String,
            debut: String,
            imageUrl: String,
            description: String,
            memorableMoments: List<String>
        ): T
    }

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, name, actor, debut, imageUrl, description, memorableMoments)
}