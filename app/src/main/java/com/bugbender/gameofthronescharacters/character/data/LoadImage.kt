package com.bugbender.gameofthronescharacters.character.data

import android.content.Context
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.ImageResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface LoadImage {

    suspend fun download(imageUrl: String): ImageResult

    class Impl @Inject constructor(
        @ApplicationContext private val context: Context,
        private val imageLoader: ImageLoader
    ) : LoadImage {

        override suspend fun download(imageUrl: String): ImageResult {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .build()

            return imageLoader.execute(request)
        }
    }
}