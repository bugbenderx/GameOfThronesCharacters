package com.bugbender.gameofthronescharacters.core.data.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterEntity
import com.bugbender.gameofthronescharacters.core.data.cache.models.CharacterWithMemorableMoments
import com.bugbender.gameofthronescharacters.core.data.cache.models.MemorableMomentEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class CharacterDaoTest {

    private lateinit var db: FavoriteCharactersDatabase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FavoriteCharactersDatabase::class.java
        ).build()
        characterDao = db.characterDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanUp() {
        db.close()
    }

    @Test
    fun allScenarios() = runTest {
        val character =
            CharacterEntity(22, "Jon Snow", "Kit Harington", "Season 1", "url1", "Description1")
        val memorableMoments = listOf(
            MemorableMomentEntity(id = 1, characterId = 22, value = "First memorable moment"),
            MemorableMomentEntity(id = 2, characterId = 22, value = "Second memorable moment")
        )

        characterDao.insertCharacter(character)
        characterDao.insertMemorableMoments(memorableMoments)

        var exists = characterDao.isExists(character.id)
        assertTrue(exists)

        val characters = characterDao.getAll()
        val expectedCharacterWithMemorableMoments = CharacterWithMemorableMoments(
            characterEntity = character,
            memorableMoments = memorableMoments
        )
        assertEquals(1, characters.size)
        assertEquals(expectedCharacterWithMemorableMoments, characters.first())

        characterDao.deleteById(character.id)
        exists = characterDao.isExists(character.id)
        assertFalse(exists)

        assertTrue(characterDao.getAll().isEmpty())
    }
}