package com.bugbender.gameofthronescharacters.core.data.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_character")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val actor: String,
    val debut: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
    val description: String,
)