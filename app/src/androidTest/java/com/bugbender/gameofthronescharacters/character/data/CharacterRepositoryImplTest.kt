package com.bugbender.gameofthronescharacters.character.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil3.Bitmap
import coil3.BitmapImage
import coil3.asImage
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.ImageResult
import coil3.request.SuccessResult
import com.bugbender.gameofthronescharacters.character.data.cache.FavoriteCharacterCacheDataSource
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterCloudDataSource
import com.bugbender.gameofthronescharacters.character.domain.Character
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import com.bugbender.gameofthronescharacters.character.domain.LoadResult
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterRepositoryImplTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var repository: CharacterRepository
    private lateinit var cloudDataSource: FakeCharacterCloudDataSource
    private lateinit var cacheDataSource: FavoriteCharacterCacheDataSource
    private lateinit var handleError: HandleError
    private val imageLoader = FakeImageLoader(context)

    @Before
    fun setUp() {
        cloudDataSource = FakeCharacterCloudDataSource()
        cacheDataSource = FakeFavoriteCharacterCacheDataSource()
        handleError = FakeHandleError()

        repository =
            CharacterRepositoryImpl(cloudDataSource, cacheDataSource, imageLoader, handleError)
    }

    @Test
    fun getRandom_should_return_success_with_character_data() = runBlocking {
        val actualResult = repository.getRandom()
        val expectedResult = LoadResult.Success(
            character = Character(
                id = 22,
                name = "Jon Snow",
                actor = "Kit Harington",
                debut = "Season 1",
                imageUrl = "url1",
                description = "Description1",
                memorableMoments = listOf("First memorable moment", "Second memorable moment"),
                isFavorite = true
            )
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getRandom_should_return_error_when_exception_occurs() = runBlocking {
        cloudDataSource.returnSuccess = false

        var actualResult = repository.getRandom()

        val expectedResult = LoadResult.Error(
            message = "serviceUnavailableTryLater",
            advice = "adviceForServiceUnavailableTryLater"
        )
        assertEquals(expectedResult, actualResult)

        cloudDataSource.returnSuccess = true
        imageLoader.shouldDownloadSucceed = false

        actualResult = repository.getRandom()
        assertEquals(expectedResult, actualResult)

        imageLoader.shouldDownloadSucceed = true

        actualResult = repository.getRandom()
        assertNotEquals(expectedResult, actualResult)
        assertTrue(actualResult is LoadResult.Success)

    }
}

class FakeCharacterCloudDataSource : CharacterCloudDataSource {

    var returnSuccess = true
    override suspend fun getRandom(): CharacterData =
        if (returnSuccess)
            CharacterData(
                id = 22,
                name = "Jon Snow",
                actor = "Kit Harington",
                debut = "Season 1",
                imageUrl = "url1",
                description = "Description1",
                memorableMoments = listOf(
                    "First memorable moment",
                    "Second memorable moment"
                )
            )
        else throw Exception()
}


class FakeImageLoader(context: Context) : LoadImage {
    var shouldDownloadSucceed = true

    private val imageRequest = ImageRequest.Builder(context).build()

    override suspend fun download(imageUrl: String): ImageResult {
        return if (shouldDownloadSucceed) {

            // Create a Bitmap (replace this with your actual Bitmap creation logic)
            val bitmap: Bitmap =
                Bitmap.createBitmap(10, 10, android.graphics.Bitmap.Config.ARGB_8888)
            val image: BitmapImage = bitmap.asImage(shareable = true)
            SuccessResult(
                image = image,
                request = imageRequest
            )
        } else {
            ErrorResult(
                image = null,
                request = imageRequest,
                throwable = Exception("Image download failed")
            )
        }
    }
}

class FakeFavoriteCharacterCacheDataSource : FavoriteCharacterCacheDataSource {
    override suspend fun save(character: CharacterData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun isExists(id: Int) = true // Pretend the character is a favorite
}

class FakeHandleError : HandleError {
    override fun handle(e: Exception): Pair<String, String> {
        return Pair("serviceUnavailableTryLater", "adviceForServiceUnavailableTryLater")
    }
}