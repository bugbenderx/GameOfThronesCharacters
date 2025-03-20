package com.bugbender.gameofthronescharacters.core.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterWithMemorableMoments
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(entity: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemorableMoments(entities: List<MemorableMomentEntity>)

    @Query("SELECT EXISTS (SELECT * FROM favorite_character WHERE id = :id)")
    suspend fun isExists(id: Int): Boolean

    @Query("SELECT * FROM favorite_character")
    fun getAll(): Flow<List<CharacterWithMemorableMoments>>

    @Query("SELECT * FROM favorite_character WHERE id=:id")
    fun getById(id: Int): Flow<CharacterWithMemorableMoments?>

    @Query("DELETE FROM favorite_character WHERE id = :id")
    suspend fun deleteById(id: Int)
}