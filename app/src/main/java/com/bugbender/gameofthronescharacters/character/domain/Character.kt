package com.bugbender.gameofthronescharacters.character.domain

data class Character(
    val id: Int,
    val name: String,
    val actor: String,
    val debut: String,
    val imageUrl: String,
    val description: String,
    val memorableMoments: List<String>,
    val isFavorite: Boolean
) {
    interface Mapper<T> {
        fun map(
            id: Int,
            name: String,
            actor: String,
            debut: String,
            imageUrl: String,
            description: String,
            memorableMoments: List<String>,
            isFavorite: Boolean
        ): T
    }

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, name, actor, debut, imageUrl, description, memorableMoments, isFavorite)
}