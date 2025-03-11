package com.bugbender.gameofthronescharacters.core.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity

@Database(
    entities = [CharacterEntity::class, MemorableMomentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteCharactersDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}